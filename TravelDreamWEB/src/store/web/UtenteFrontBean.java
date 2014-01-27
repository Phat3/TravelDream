package store.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.ListaDesideriDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.PrenotazioneDTO;
import traveldream.dtos.ShareDTO;
import traveldream.manager.ListaDesideriMng;
import traveldream.manager.PrenotazioneMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "utenteFrontBean")
@SessionScoped
public class UtenteFrontBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 781977263206659707L;
	
	private List<ListaDesideriDTO> listaDesideri;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	@EJB
	private PrenotazioneMng prenotazioneMng;
	
	private List<PrenotazioneDTO> prenotazioni;
	
	public UtenteFrontBean(){
		this.listaDesideri = new ArrayList<ListaDesideriDTO>();
		
	}

	public List<ListaDesideriDTO> getListaDesideri() {
		this.listaDesideri = listaDesideriMng.getListaDesisderiUtente(this.userMgr.getUserDTO());
		System.out.println("grandezza lista" + listaDesideri.size());
		
		for (ListaDesideriDTO pacchettoDTO : this.listaDesideri) {
			System.out.println(pacchettoDTO.getPacchetto().getOk());
		}
		for (Iterator<ListaDesideriDTO> s = this.listaDesideri.iterator(); s.hasNext(); ) {
			
			ListaDesideriDTO listaDaControllare = s.next();
			if (listaDaControllare.getPacchetto().getOk().equals("X") || listaDaControllare.getPacchetto().getEliminato() == 1) {
				s.remove();
			}
		}
		System.out.println("grandezza lista dopo" + listaDesideri.size());
		
		return this.listaDesideri;
	}

	public void setListaDesideri(List<ListaDesideriDTO> listaDesideri) {
		this.listaDesideri = listaDesideri;
	}
	
	public void deletePacchetto(ListaDesideriDTO lista){
		System.out.println("benissimo");
		this.listaDesideriMng.eliminaDaListaDesideri(lista);
		this.listaDesideri.remove(lista);
	}

	public List<PrenotazioneDTO> getPrenotazioni() {
		prenotazioni = prenotazioneMng.getPrenotazioniUtente(userMgr.getUserDTO());
		return prenotazioni;
	}

	public void setPrenotazioni(List<PrenotazioneDTO> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	

}

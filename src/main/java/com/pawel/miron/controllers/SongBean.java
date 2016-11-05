/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawel.miron.controllers;

import com.pawel.miron.config.DBManager;
import com.pawel.miron.enitity.Album;
import com.pawel.miron.enitity.Song;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;

/**
 *
 * @author Pawel
 */
@SessionScoped
@ManagedBean(name = "SongBean")
public class SongBean implements Serializable {

    private Song song = new Song();
   
    public SongBean() {
        System.out.println("Stworzono SongBean");
    }

    public Song getSong() {
        return this.song;
    }

    public void setAlbum(Song song) {
        this.song = song;
    }

    public String dodaj() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        song.setId(null);
        em.persist(song);
        em.getTransaction().commit();
        this.dodajInfo("Dodano utwor!");
        em.close();
        this.song = new Song();
        return null;
    }

    public List<Song> getAllSongs() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createNamedQuery("Song.findAll").getResultList();
        em.close();
        return list;
    }

    public String zaladujDoEdycji() {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.song = em.find(Song.class, song.getId());
        em.close();
        return null;
    }

    public String usun() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.song = em.find(Song.class, song.getId());
        em.remove(this.song);
        this.song = new Song();
        em.getTransaction().commit();
        em.close();
        this.dodajInfo("Usunieto album");
        return null;
    }

    public void dodajInfo(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }

    public String edytuj() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        em.merge(song);
        em.getTransaction().commit();
        em.close();
        this.dodajInfo("Zmieniono dane utworu");
        this.song = new Song();
        return null;
    }

    public void songListener() {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("songID");
        int id = Integer.parseInt(ids);
        this.song.setId(id);
    }


}

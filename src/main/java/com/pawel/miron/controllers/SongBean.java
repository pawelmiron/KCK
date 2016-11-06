/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawel.miron.controllers;

import com.pawel.miron.config.DBManager;
import com.pawel.miron.enitity.Song;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;
import java.util.Scanner;
import javazoom.jl.player.advanced.AdvancedPlayer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Pawel
 */
@SessionScoped
@ManagedBean(name = "SongBean")
public class SongBean implements Serializable {

    private Song song = new Song();
    @ManagedProperty(value = "#{AlbumBean}")
    private AlbumBean albumBean;

    private Part file; // +getter+setter

    public void save() {
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, new File("C:/Users/Pawel/Desktop/mavenproject2/src/main/resources/music", file.getSubmittedFileName()).toPath());
            song.setTitle("C:/Users/Pawel/Desktop/mavenproject2/src/main/resources/music/" + file.getSubmittedFileName());
        } catch (IOException e) {
            System.out.println("Zapisywanie nie powiodlo się");
        }
    }

    public void play(String in) {
        Scanner read = new Scanner(System.in);
        System.out.println("Podaj ścieżkę do pliku Mp3");
        String filepath = in; /// odczytujemy wpisaną ścieżkę
        try {
            FileInputStream fis = new FileInputStream(filepath);
            AdvancedPlayer advancedPlayer = new AdvancedPlayer(fis);
            System.out.println("Odtwarzanie pliku " + filepath);
            advancedPlayer.play(500, 1000);
        } catch (FileNotFoundException | JavaLayerException exc) {
            System.out.println("Nie można otworzyc pliku MP3");
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public AlbumBean getAlbumBean() {
        return albumBean;
    }

    public void setAlbumBean(AlbumBean albumBean) {
        this.albumBean = albumBean;
    }

    public void setSong(Song song) {
        this.song = song;
    }

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
        save();
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

    public Set<Song> getAlbumSongs() {
        //EntityManager em = DBManager.getManager().createEntityManager();
        // Query query = em.createNamedQuery("Song.findByAlbum");
        // Integer id = albumBean.getAlbum().getId();
        // query.setParameter("id", id.toString());
        // List list = query.getResultList();
        // em.close();
        // return list;
        return albumBean.getAlbum().getSongSet();
    }

    public String zaladujDoEdycji() {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.song = em.find(Song.class, song.getId());
        em.close();
        return "edytujUtwor.xhtml";
    }

    public String usun() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.song = em.find(Song.class, song.getId());
        em.remove(this.song);
        this.song = new Song();
        em.getTransaction().commit();
        em.close();
        this.dodajInfo("Usunieto utwor");
        return "pokazAlbumy.xhtml";
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
        return "pokazAlbumy.xhtml";
    }

    public void songListener() {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("songID");
        int id = Integer.parseInt(ids);
        this.song.setId(id);
    }

}

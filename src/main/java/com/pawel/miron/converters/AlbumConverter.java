/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawel.miron.converters;

import com.pawel.miron.config.DBManager;
import com.pawel.miron.enitity.Album;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author Pawel
 */
@FacesConverter("AlbumConverter")
public class AlbumConverter implements Converter {

    /**
     *
     * @param ctx
     * @param c
     * @param o
     * @return
     */
    @Override
    public String getAsString(FacesContext ctx, UIComponent c, Object o) {
        if (!(o instanceof Album)) {
            throw new ConverterException(new FacesMessage("Nie udalo sie dokonac konwersji"));
        }
        Album a = (Album) o;
        return a.getId().toString();
    }

    /**
     *
     * @param ctx
     * @param c
     * @param s
     * @return
     */
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent c, String s) {
        Integer i = Integer.valueOf(s);
        EntityManager em = DBManager.getManager().createEntityManager();
        Album a = em.find(Album.class, i);
        em.close();
        return a;
    }
}

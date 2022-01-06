package org.magadiflo.hibernate.app.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Todo lo que contenga esta clase Auditoria
 * va a formar parte de la clase donde la estemos incluyendo
 * ya que estamos usando la anotación @Embeddable. Es decir,
 * es una clase que se puede reutilizar, incluir en otra clase
 * Entity
 */

@Embeddable
public class Auditoria {

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "editado_en")
    private LocalDateTime editadoEn;

    @PrePersist
    public void prePersist() {
        System.out.println("Inicializar algo justo antes del persist");
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("Inicializar algo justo antes del update");
        this.editadoEn = LocalDateTime.now();
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getEditadoEn() {
        return editadoEn;
    }

    public void setEditadoEn(LocalDateTime editadoEn) {
        this.editadoEn = editadoEn;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;


import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.dao.CategoriaDao;
import br.com.hibernatepetshop.dao.CategoriaDaoImpl;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author gusta
 */
@ManagedBean(name = "categoriaC")
@ViewScoped
public class CategoriaControle {

    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private Session session;
    private DataModel<Categoria> modelCategorias;
    private int numeroAba = 0;

    public void pesquisarPorNome() {
        try {
            categoriaDao = new CategoriaDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Categoria> categorias = categoriaDao.pesquisarPorNome(categoria.getNome(), session);
            modelCategorias = new ListDataModel(categorias);
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void excluir() {
        categoria = modelCategorias.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            categoriaDao = new CategoriaDaoImpl();
            categoriaDao.remover(categoria, session);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
            categoria.setNome(null);
            modelCategorias = null;
        } catch (Exception e) {
            System.out.println("Erro ao excluir" + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Excluír!", ""));

        } finally {
            session.close();
        }
    }

    public void alterar() {
        numeroAba = 1;
        categoria = modelCategorias.getRowData();
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        categoriaDao = new CategoriaDaoImpl();
        session = HibernateUtil.abrirSessao();
        try {
            categoriaDao.salvarOuAlterar(categoria, session);
            context.addMessage(null, new FacesMessage("Salvo com sucesso!", ""));
            categoria = new Categoria();
            numeroAba = 0;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Salvar!" + e.getMessage(), ""));
        } finally {
            session.close();
        }
    }

    public Categoria getCategoria() {
        if (categoria == null) {
            categoria = new Categoria();
        }
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ListDataModel getModelCategorias() {
        return (ListDataModel) modelCategorias;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }

}

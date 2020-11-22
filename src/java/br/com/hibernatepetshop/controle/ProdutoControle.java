/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.ProdutoDao;
import br.com.hibernatepetshop.dao.ProdutoDaoImpl;
import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.entidade.Produto;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;

/**
 *
 * @author gusta
 */
@ManagedBean(name = "produtoC")
@ViewScoped
public class ProdutoControle {

    private Produto produto;
    private ProdutoDao produtoDao;
    private Session session;
    private DataModel<Produto> modelProdutos;
    private int numeroAba = 0;

    public void pesquisarPorNome() {
        try {
            produtoDao = new ProdutoDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Produto> produtos = produtoDao.pesquisarPorNome(produto.getNome(), session);
            modelProdutos = new ListDataModel(produtos);
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void excluir() {
        produto = modelProdutos.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            produtoDao = new ProdutoDaoImpl();
            produtoDao.remover(produto, session);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
            produto.setNome(null);
            modelProdutos = null;
        } catch (Exception e) {
            System.out.println("Erro ao excluir" + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Excluír!", ""));

        } finally {
            session.close();
        }
    }

    public void alterar() {
        numeroAba = 1;
        produto = modelProdutos.getRowData();
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        produtoDao = new ProdutoDaoImpl();
        session = HibernateUtil.abrirSessao();
        try {
            produtoDao.salvarOuAlterar(produto, session);
            context.addMessage(null, new FacesMessage("Salvo com sucesso!", ""));
            produto = new Produto();
            numeroAba = 0;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Salvar!" + e.getMessage(), ""));
        } finally {
            session.close();
        }
    }

    //getters e setters
    public Produto getProduto() {
        if (produto == null) {
            produto = new Produto();
        }
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ListDataModel getModelProdutos() {
        return (ListDataModel) modelProdutos;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }
}

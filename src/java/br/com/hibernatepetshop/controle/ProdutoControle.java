/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.CategoriaDao;
import br.com.hibernatepetshop.dao.CategoriaDaoImpl;
import br.com.hibernatepetshop.dao.FornecedorDao;
import br.com.hibernatepetshop.dao.FornecedorDaoImpl;
import br.com.hibernatepetshop.dao.ProdutoDao;
import br.com.hibernatepetshop.dao.ProdutoDaoImpl;
import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;

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
    private List<SelectItem> itensCategorias;
    private List<SelectItem> itensFornecedores;
    private Fornecedor fornecedor;
    private Categoria categoria;

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

    public void onTabChange(TabChangeEvent event) {
        produto = new Produto();
        if (event.getTab().getTitle().equals("Novo")) {
            if (itensFornecedores == null) {
                carregarComboboxFornecedor();
            }
            if (itensCategorias == null) {
                carregarComboboxCategoria();
            }
        }
    }

    public void carregarComboboxFornecedor() {
        FornecedorDao fornecedorDao = new FornecedorDaoImpl();
        session = HibernateUtil.abrirSessao();
        itensFornecedores = new ArrayList<>();
        try {
            List<Fornecedor> listaFornecedores = fornecedorDao.pesquisarTodo(session);
            for (Fornecedor forn : listaFornecedores) {
                itensFornecedores.add(new SelectItem(forn.getId(), forn.getNome()));
            }
        } catch (HibernateException e) {
            System.out.println("Erro ao carregar a lista");
        } finally {
            session.close();
        }
    }

    public void carregarComboboxCategoria() {
        CategoriaDao categoriaDao = new CategoriaDaoImpl();
        session = HibernateUtil.abrirSessao();
        itensCategorias = new ArrayList<>();
        try {
            List<Categoria> listaCategorias = categoriaDao.pesquisarTodo(session);
            for (Categoria cat : listaCategorias) {
                itensCategorias.add(new SelectItem(cat.getId(), cat.getNome()));
            }
        } catch (HibernateException e) {
            System.out.println("Erro ao carregar a lista");
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
            produto.setFornecedor(fornecedor);
            produto.setCategoria(categoria);
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

    public List<SelectItem> getItensFornecedores() {
        return itensFornecedores;
    }

    public Fornecedor getFornecedor() {
        if (fornecedor == null) {
            fornecedor = new Fornecedor();
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<SelectItem> getItensCategorias() {
        return itensCategorias;
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.FornecedorDao;
import br.com.hibernatepetshop.dao.FornecedorDaoImpl;
import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.entidade.Fornecedor;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;
import static org.primefaces.behavior.confirm.ConfirmBehavior.PropertyKeys.message;

/**
 *
 * @author gusta
 */
@ManagedBean(name = "fornecedorC")
@ViewScoped
public class FornecedorControle {

    private Fornecedor fornecedor;
    private FornecedorDao fornecedorDao;
    private Session session;
    private DataModel<Fornecedor> modelFornecedores;
    private int numeroAba = 0;

    public void pesquisarPorNome() {
        try {
            fornecedorDao = new FornecedorDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Fornecedor> fornecedores = fornecedorDao.pesquisarPorNome(fornecedor.getNome(), session);
            modelFornecedores = new ListDataModel(fornecedores);
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void excluir() {
        fornecedor = modelFornecedores.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            fornecedorDao = new FornecedorDaoImpl();
            fornecedorDao.remover(fornecedor, session);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
            fornecedor.setNome(null);
            modelFornecedores = null;
        } catch (Exception e) {
            System.out.println("Erro ao excluir" + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Excluír!", ""));

        } finally {
            session.close();
        }
    }

    public void alterar() {
        numeroAba = 1;
        fornecedor = modelFornecedores.getRowData();
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        fornecedorDao = new FornecedorDaoImpl();
        session = HibernateUtil.abrirSessao();
        try {
            fornecedor.setDataCadastro(new Date());
            fornecedorDao.salvarOuAlterar(fornecedor, session);
            context.addMessage(null, new FacesMessage("Salvo com sucesso!", ""));
            fornecedor = new Fornecedor();
            numeroAba = 0;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Salvar!" + e.getMessage(), ""));
        } finally {
            session.close();
        }
    }

    //getters e setters
    public Fornecedor getFornecedor() {
        if (fornecedor == null) {
            fornecedor = new Fornecedor();
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ListDataModel getModelFornecedores() {
        return (ListDataModel) modelFornecedores;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }

}

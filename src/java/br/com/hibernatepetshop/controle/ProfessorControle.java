/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.TelefoneDaoImpl;
import br.com.hibernatepetshop.dao.ProfessorDao;
import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.dao.TelefoneDao;
import br.com.hibernatepetshop.entidade.Telefone;
import br.com.hibernatepetshop.dao.ProfessorDaoImpl;
import br.com.hibernatepetshop.entidade.Endereco;
import br.com.hibernatepetshop.entidade.Professor;
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
@ManagedBean(name = "professorC")
@ViewScoped
public class ProfessorControle {

    private Professor professor;
    private ProfessorDao professorDao;
    private Session session;
    private DataModel<Professor> modelProfessores;
    private List<Telefone> telefones;
    private int numeroAba = 0;
    private Telefone telefone;
    private Endereco endereco;

    public void pesquisarPorNome() {
        try {
            professorDao = new ProfessorDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Professor> professores = professorDao.pesquisarPorNome(professor.getNome(), session);
            modelProfessores = new ListDataModel(professores);
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void excluir() {
        professor = modelProfessores.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            professorDao = new ProfessorDaoImpl();
            professorDao.remover(professor, session);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
            professor.setNome(null);
            modelProfessores = null;
        } catch (Exception e) {
            System.out.println("Erro ao excluir" + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Excluír!", ""));

        } finally {
            session.close();
        }
    }

    public void excluirTelefone(Telefone telefone) {
        telefones.remove(telefone);
        if (telefone.getId() == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            TelefoneDao telefoneDao = new TelefoneDaoImpl();
            session = HibernateUtil.abrirSessao();
            try {
                telefoneDao.remover(telefone, session);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
                numeroAba = 0;
            } catch (Exception e) {
                System.out.println("Erro ao excluir telefone " + e.getMessage());
            } finally {
                session.close();
            }
        }
    }

    public void alterar() {
        numeroAba = 1;
        professor = modelProfessores.getRowData();
        telefones = professor.getTelefones();
//        endereco = professor.getEndereco();

    }

    public void carregarTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public void onRowCancelT(RowEditEvent<Telefone> event) {
        System.out.println("Cancelando alterar telefone");
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        professorDao = new ProfessorDaoImpl();
        session = HibernateUtil.abrirSessao();
        try {
            professor.setEndereco(endereco);
            professorDao.salvarOuAlterar(professor, session);
            context.addMessage(null, new FacesMessage("Salvo com sucesso!", ""));
            professor = new Professor();
            numeroAba = 0;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Salvar!" + e.getMessage(), ""));
        } finally {
            session.close();
        }
    }

    public void salvarTelefone() {
        if (telefones == null) {
            telefones = new ArrayList<>();
            professor.setTelefones(telefones);
        }
        if (telefone.getId() == null) {
            telefones.add(telefone);
        }
        telefone.setPessoaSenac(professor);
    }
    
    public void novoTelefone() {
        telefone = new Telefone();
    }

    public void carregarEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void onRowCancelE(RowEditEvent<Endereco> event) {
        System.out.println("Cancelando alterar endereço");
    }

    public void salvarEndereco() {
        endereco.setPessoaSenac(professor);
    }

    //getters e setters
    public Professor getProfessor() {
        if (professor == null) {
            professor = new Professor();
        }
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public ListDataModel getModelProfessores() {
        return (ListDataModel) modelProfessores;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public Telefone getTelefone() {
        if (telefone == null) {
            telefone = new Telefone();
        }
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

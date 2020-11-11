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
import com.mysql.cj.x.protobuf.MysqlxCrud.DataModel;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;

/**
 *
 * @author gusta
 */

@ManagedBean (name = "fornecedorC")
@ViewScoped
public class FornecedorControle {
    
    private Fornecedor fornecedor;
    private FornecedorDao fornecedorDao;
    private Session session;
    private ListDataModel modelFornecedores;
    
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
    
    //getters e setters

    public Fornecedor getFornecedor() {
        if(fornecedor == null) {
            fornecedor = new Fornecedor();
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ListDataModel getModelFornecedores() {
        return modelFornecedores;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gusta
 */
public class FornecedorDaoImplTest {
    
    private Fornecedor fornecedor;
    private FornecedorDao fornecedorDao;
    private Session sessao;
    
    public FornecedorDaoImplTest() {
        fornecedorDao = new FornecedorDaoImpl();
    }
    
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        fornecedor = new Fornecedor(null,
            "Nome" + UtilTeste.gerarCaracter(7),
            UtilTeste.gerarEmail(),
            UtilTeste.gerarTelefone(),
            "bla bla bla",
            new Date()
        );
        
        sessao = HibernateUtil.abrirSessao();
        fornecedorDao.salvarOuAlterar(fornecedor, sessao);
        sessao.close();
        assertNotNull(fornecedor.getId());
    }

//    @Test;
    public void testPesquisarPorId() {
        
        Session sessao = null;
    }

//    @Test
    public void testPesquisarPorNome() {
        
        Session sessao = null;
    }

//    @Test
    public void testPesquisarTodo() {
        
        Session sessao = null;
    }
    
    public Fornecedor buscarFornecedorBd() {
        Long id;
        sessao = HibernateUtil.abrirSessao();
        
        try {
            Query consulta = sessao.createQuery("SELECT max(id) FROM Fornecedor");
            id = (Long) consulta.uniqueResult();
            if(id == null) {
                sessao.close();
                testSalvar();
            } else {
                fornecedor = fornecedorDao.pesquisarPorId(id, sessao);
                sessao.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar buscarFonecedorBd " + e.getMessage());
        }
        return fornecedor;
    }
    
}

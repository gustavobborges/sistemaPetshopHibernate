/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Produto;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
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
    
//    @Test
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
    
//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarFornecedorBd();
        fornecedor.setNome("nome alterador");
        fornecedor.setEmail("e-mail alterado");
                
        sessao = HibernateUtil.abrirSessao();
        fornecedorDao.salvarOuAlterar(fornecedor, sessao);
        Fornecedor fornecedorAlt = fornecedorDao.pesquisarPorId(fornecedor.getId(), sessao);
        sessao.close();
        assertEquals(fornecedor.getNome(), fornecedorAlt.getNome());
        assertEquals(fornecedor.getEmail(), fornecedorAlt.getEmail());

    }
    
//    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarFornecedorBd();
        
        sessao = HibernateUtil.abrirSessao();
        fornecedorDao.remover(fornecedor, sessao);
        Fornecedor fornecedorExcluido = fornecedorDao.pesquisarPorId(fornecedor.getId(), sessao);
        sessao.close();
        assertNull(fornecedorExcluido);
    }
    

//    @Test;
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarFornecedorBd();
        Long id = fornecedor.getId();
        sessao = HibernateUtil.abrirSessao();
        Fornecedor fornecedorId = fornecedorDao.pesquisarPorId(id, sessao);
        sessao.close();
        assertNotNull(fornecedorId);
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("PesquisarPorNome");
        buscarFornecedorBd();
        sessao = HibernateUtil.abrirSessao();
        List <Fornecedor> fornecedores = fornecedorDao.pesquisarPorNome(fornecedor.getNome(), sessao);
        sessao.close();
        assertTrue(fornecedores.size() > 0);
    }

//    @Test
    public void testPesquisarTodo() {
        System.out.println("pesquisarTodo");
        buscarFornecedorBd();
        sessao = HibernateUtil.abrirSessao();
        List <Fornecedor> fornecedores = fornecedorDao.pesquisarTodo(sessao);
        sessao.close();
        assertTrue(fornecedores.size() > 0);
    }
    
    @Test
    public void testPesquisarPorNomeProduto() {
        System.out.println("pesquisarPorNomeProduto");
        
        sessao = HibernateUtil.abrirSessao();
        fornecedor = fornecedorDao.pesquisarPorNomeProduto("escada", sessao);
        sessao.close();
        System.out.println("Fornecedor: " + fornecedor.getNome());
        for (Produto prod : fornecedor.getProdutos()) {
            System.out.println("Produto " + prod.getNome());
            System.out.println("Estoque " + prod.getEstoque());
            System.out.println(" "); 
        };


    }
    
  public Fornecedor buscarFornecedorBd(){
        Long id;
        sessao = HibernateUtil.abrirSessao();
        try {
            Query consulta = sessao.createQuery("SELECT max(id) FROM Fornecedor");
            id = (Long) consulta.uniqueResult();
            if(id == null){
                sessao.close();
                testSalvar();
            }else{
               fornecedor = fornecedorDao.pesquisarPorId(id, sessao);
               sessao.close();
            }
        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar buscarFornecedorBd " + e.getMessage());
        }
        return fornecedor;
    }


    
}

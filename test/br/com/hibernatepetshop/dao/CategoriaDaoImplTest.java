/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Produto;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gusta
 */
public class CategoriaDaoImplTest {
    
    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private Session session;
    
    public CategoriaDaoImplTest() {
        categoriaDao = new CategoriaDaoImpl();
    }
    
//    @Test
    public void testSalvar() {
        System.out.println("testSalvar");
        categoria = new Categoria(null, "Categoria " + UtilTeste.gerarCaracter(10), "bla, bla..");
        session = HibernateUtil.abrirSessao();
        categoriaDao.salvarOuAlterar(categoria, session);
        session.close();
        assertNotNull(categoria.getId());
    }
//    @Test
    public void testAlterar() {
        System.out.println("testAlterar");
        buscarCategoriaBD();
        categoria.setNome("Categoria alterada: " + UtilTeste.gerarCaracter(5));
        session = HibernateUtil.abrirSessao();
        categoriaDao.salvarOuAlterar(categoria, session);
        Categoria categoriaAlt = categoriaDao.pesquisarPorId(categoria.getId(), session);
        session.close();
        assertEquals(categoria.getNome(), categoriaAlt.getNome());
    }
    
//    @Test
    public void testExcluir() {
        System.out.println("testExcluir");
        buscarCategoriaBD();
        session = HibernateUtil.abrirSessao();
        categoriaDao.remover(categoria, session);
        Categoria categoriaExc = categoriaDao.pesquisarPorId(categoria.getId(), session);
        session.close();
        assertNull(categoriaExc);
    }
    

//    @Test
    public void testPesquisarPorId() {
        System.out.println("testPesquisarPorId");
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("testPesquisarPorNome");
        buscarCategoriaBD();
        session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = categoriaDao.pesquisarPorNome(categoria.getNome(), session);
        session.close();
        assertTrue(categorias.size() > 0);
    }

//    @Test
    public void testPesquisarTodo() {
        System.out.println("pesquisarTodo");
        buscarCategoriaBD();
        session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = categoriaDao.pesquisarTodo(session);
        session.close();
        assertTrue(!categorias.isEmpty());
    }

//    @Test
    public void testPesquisarPorNomeProduto() {
        System.out.println("pesquisarPorNomeProduto");
        session = HibernateUtil.abrirSessao();
        categoria = categoriaDao.pesquisarPorNomeProduto("nome", session);
        session.close();
        System.out.println("Categoria: " + categoria.getNome());
        for (Produto produto : categoria.getProdutos()) {
            System.out.println("Produto: " + produto.getNome());
            System.out.println("");
        }
        
    }
 
    public Categoria buscarCategoriaBD() {
        session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Categoria");
        List<Categoria> categorias = consulta.list();
        if(categorias.isEmpty()) {
            session.close();
            testSalvar();
        } else {
            categoria = categorias.get(0);
            session.close();
        }
        return categoria;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Produto;
import br.com.hibernatepetshop.util.UtilTeste;
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
public class ProdutoDaoImplTest {
    private Produto produto;
    private Fornecedor fornecedor;
    private ProdutoDao produtoDao;
    private Session session;
    private FornecedorDaoImplTest fornecedorDaoImpl;
     
    public ProdutoDaoImplTest() {
        produtoDao = new ProdutoDaoImpl();
    }
    
//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        produto = new Produto(
                null,
                "Nome" + UtilTeste.gerarCaracter(5),
                UtilTeste.gerarNumero(5),
                "Bla, Bla, Bla..",
                Double.parseDouble(UtilTeste.gerarNumero(3)),
                Integer.parseInt(UtilTeste.gerarNumero(3))
        );
        
        fornecedorDaoImpl = new FornecedorDaoImplTest();
        fornecedor = fornecedorDaoImpl.buscarFornecedorBd();
        produto.setFornecedor(fornecedor);
        
        session = HibernateUtil.abrirSessao();
        produtoDao.salvarOuAlterar(produto, session);
        session.close();
        assertNotNull(produto.getId());
    }
    
//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarProdutoBd();
        produto.setNome("Produto Alterado");
        session = HibernateUtil.abrirSessao();
        produtoDao.salvarOuAlterar(produto, session);
        Produto produtoAlt = produtoDao.pesquisarPorId(produto.getId(), session);        
        session.close();               
        assertEquals(produto.getNome(), produtoAlt.getNome());
    }
    
//    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarProdutoBd();
        session = HibernateUtil.abrirSessao();
        produtoDao.remover(produto, session);
        Produto produtoExcluido = produtoDao.pesquisarPorId(produto.getId(), session);        
        session.close();               
        assertNull(produtoExcluido);
    }
    
//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }
    
    //    @Test
    public void testPesquisarPorFornecedor() {
        System.out.println("pesquisarPorFornecedor");
        buscarProdutoBd();
        session = HibernateUtil.abrirSessao();
        List<Produto> produtos = produtoDao.pesquisarPorFornecedor(fornecedor.getNome(), session);
        session.close();
        assertTrue(produtos.size() > 0);            
    }
    
    
//    @Test
    public void testPesquisarPorProdutoEstoque() {
        System.out.println("pesquisarPorProdutoEstoque");
        buscarProdutoBd();
        int qtd = produto.getEstoque();
        session = HibernateUtil.abrirSessao();
        List<Produto> produtos = produtoDao.pesquisarPorProdutoEstoque(qtd, produto.getNome(), session);
        assertTrue(produtos.size() > 0);

    }
    @Test
    public void testPesquisarPorPrecoMinimoMaximo() {
        System.out.println("pesquisarPorPrecoMinimoMaximo");
        buscarProdutoBd();
        session = HibernateUtil.abrirSessao();
        List<Produto> produtos = produtoDao.pesquisarPorPrecoMinimoMaximo((produto.getPreco() - 20), produto.getPreco(), session);
        System.out.println("Tamanho: " + produtos.size());
        assertTrue(!produtos.isEmpty());
    }
    
//    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProdutoBd();
        session = HibernateUtil.abrirSessao();
        List <Produto> produtos = produtoDao.pesquisarPorNome(produto.getNome(), session);
        session.close();
        
        System.out.println("nome: " + produtos.get(0).getNome());
        System.out.println("fornecedor: " + produtos.get(0).getFornecedor().getNome());
        
        assertTrue(produtos.size() > 0);
    }

    public Produto buscarProdutoBd(){
      Long id;
      session = HibernateUtil.abrirSessao();
      try {
          Query consulta = session.createQuery("SELECT max(id) FROM Produto");
          id = (Long) consulta.uniqueResult();
          if(id == null){
              session.close();
              testSalvar();
          }else{
             produto = produtoDao.pesquisarPorId(id, session);
             session.close();
          }
      } catch (HibernateException e) {
          System.err.println("Erro ao pesquisar buscarProdutoBd " + e.getMessage());
      }
      return produto;
    }






    
}

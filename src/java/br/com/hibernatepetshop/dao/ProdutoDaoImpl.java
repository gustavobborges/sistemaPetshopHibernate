/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Produto;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gusta
 */
public class ProdutoDaoImpl extends BaseDaoImpl<Produto, Long> implements ProdutoDao{

    @Override
    public Produto pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Produto) session.get(Produto.class, id);
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("FROM Produto WHERE nome LIKE :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}

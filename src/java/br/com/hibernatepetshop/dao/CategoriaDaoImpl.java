/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Categoria;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gusta
 */
public class CategoriaDaoImpl extends BaseDaoImpl<Categoria, Long> implements CategoriaDao{

    @Override
    public Categoria pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Categoria) session.get(Categoria.class, id); 
    }

    @Override
    public List<Categoria> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Categoria WHERE nome LIKE :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Categoria> pesquisarTodo(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Categoria");
        return consulta.list();
    }

    @Override
    public Categoria pesquisarPorNomeProduto(String nomeProduto, Session session) throws HibernateException {
        Query consulta = session.createQuery("SELECT DISTINCT(c) from Categoria c JOIN FETCH c.produtos p WHERE p.nome LIKE :produto");
        consulta.setParameter("produto", "%" + nomeProduto + "%");
        return (Categoria) consulta.uniqueResult();
    }
    
}

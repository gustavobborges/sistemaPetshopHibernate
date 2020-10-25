/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Fornecedor;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class FornecedorDaoImpl extends BaseDaoImpl<Fornecedor, Long> implements FornecedorDao{

    @Override
    public Fornecedor pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Fornecedor) sessao.get(Fornecedor.class, id);
    }

    @Override
    public List<Fornecedor> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("FROM Fornecedor WHERE nome LIKE :nomeHQL");
        consulta.setParameter("nomeHQL", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Fornecedor> pesquisarTodo(Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("FROM Fornecedor");
        return consulta.list();   
    }

    @Override
    public Fornecedor pesquisarPorNomeProduto(String nomeProduto, Session session) throws HibernateException {
        Query consulta = session.createQuery("SELECT DISTINCT(f) FROM Fornecedor f join fetch f.produtos p WHERE p.nome LIKE :nomeProduto");
        consulta.setParameter("nomeProduto", "%" + nomeProduto + "%");
        return (Fornecedor) consulta.uniqueResult();   
    }
    
}

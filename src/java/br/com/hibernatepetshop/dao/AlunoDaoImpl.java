/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Aluno;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gusta
 */
public class AlunoDaoImpl extends BaseDaoImpl<Aluno, Long> implements AlunoDao, Serializable {
    @Override
    public Aluno pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Aluno) session.get(Aluno.class, id);
    }

    @Override
    public List<Aluno> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("SELECT DISTINCT(p) from Aluno p JOIN FETCH p.telefones WHERE p.nome like :nomeAluno");
        consulta.setParameter("nomeAluno", "%" + nome + "%");
        return consulta.list();
    }
    
}

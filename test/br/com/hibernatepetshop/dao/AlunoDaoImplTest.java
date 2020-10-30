/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Aluno;
import br.com.hibernatepetshop.entidade.Endereco;
import br.com.hibernatepetshop.entidade.Professor;
import br.com.hibernatepetshop.entidade.Telefone;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gusta
 */
public class AlunoDaoImplTest {
    
    private Aluno aluno;
    private AlunoDao alunoDao;
    private Session session;
    
    public AlunoDaoImplTest() {
        alunoDao = new AlunoDaoImpl();        
    }
    
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        aluno = new Aluno(null,
                "Nome " + UtilTeste.gerarCaracter(5),
                UtilTeste.gerarCpf(),
                UtilTeste.gerarNumero(5),
                UtilTeste.gerarEmail(),
                UtilTeste.gerarNumero(4)
        );              
        Endereco endereco = new Endereco(null,
            "Rua " + UtilTeste.gerarCaracter(7), 
            UtilTeste.gerarNumero(3), 
            "Bairro " + UtilTeste.gerarCaracter(7),
            "Cidade " + UtilTeste.gerarCaracter(7), 
            "SC", 
            "88888-888", 
            "casa"
        );
        aluno.setEndereco(endereco);
        endereco.setPessoaSenac(aluno);
        List<Telefone> telefones = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            telefones.add(criarTelefone());
        }
        
        aluno.setTelefones(telefones);
        for (Telefone telefone : telefones) {
            telefone.setPessoaSenac(aluno);
        }
        
        session = HibernateUtil.abrirSessao();
        alunoDao.salvarOuAlterar(aluno, session);
        session.close();
        assertNotNull(endereco.getId());
        assertNotNull(aluno.getId());
        assertNotNull(aluno.getTelefones().get(0).getId());
    }
    
    private Telefone criarTelefone() {
        Telefone telefone = new Telefone(null,
                UtilTeste.gerarTelefone(),
                "celular",
                "Tim"
        );
        return telefone;
    }
    
//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarAlunoBD();
        aluno.setNome(UtilTeste.gerarCaracter(5));
        aluno.getEndereco().setLogradouro("Av. " + UtilTeste.gerarCaracter(5));
        aluno.getTelefones().get(0).setNumero(UtilTeste.gerarTelefone());
        
        session = HibernateUtil.abrirSessao();
        alunoDao.salvarOuAlterar(aluno, session);
        
        Aluno alunoAlterado = alunoDao.pesquisarPorId(aluno.getId(), session);
        Telefone telefone = alunoAlterado.getTelefones().get(0);
        session.close();
        
        assertEquals(aluno.getNome(), alunoAlterado.getNome());
        assertEquals(aluno.getEndereco().getLogradouro(), alunoAlterado.getEndereco().getLogradouro());
        assertEquals(aluno.getTelefones().get(0).getNumero(), telefone.getNumero());
     }
    
//    @Test
    public void testExcluir() {
        System.out.println("Escluir");
        buscarAlunoBD();
        session = HibernateUtil.abrirSessao();
        alunoDao.remover(aluno, session);
        Aluno alunoExcluido = alunoDao.pesquisarPorId(aluno.getId(), session);
        session.close();  
        assertNull(alunoExcluido);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
         buscarAlunoBD();
        session = HibernateUtil.abrirSessao();
        List<Aluno> alunos = alunoDao.pesquisarPorNome(aluno.getNome(), session);
        assertTrue(alunos.size() > 0);
    }
    
    public Aluno buscarAlunoBD() {
    session = HibernateUtil.abrirSessao();
    Query consulta = session.createQuery("SELECT DISTINCT(p) FROM Aluno p JOIN FETCH p.telefones");
    List<Aluno> alunos = consulta.list();
    if(alunos.isEmpty()) {
        session.close();
        testSalvar();
    } else {
        aluno = alunos.get(0);
        session.close();
    }
    return aluno;
    }
}

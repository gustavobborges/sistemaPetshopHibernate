package br.com.hibernatepetshop.entidade;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fornecedor.class)
public abstract class Fornecedor_ {

	public static volatile SingularAttribute<Fornecedor, String> telefone;
	public static volatile SingularAttribute<Fornecedor, String> nome;
	public static volatile SingularAttribute<Fornecedor, Long> id;
	public static volatile SingularAttribute<Fornecedor, Date> dataCadastro;
	public static volatile SingularAttribute<Fornecedor, String> email;
	public static volatile SingularAttribute<Fornecedor, String> descricao;

}


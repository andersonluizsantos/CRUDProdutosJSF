package com.fuctura.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.fuctura.modelo.Produto;
import com.fuctura.util.JpaUtil;

@ManagedBean
@SessionScoped
public class ProdutoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private Double preco;
	private Collection<Produto> produtos;
	private Produto produtoSelecionado;

	public String salvar() {

		/*Código para Inserir no ArrayList
		
		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setPreco(preco);
		produtos.add(produto);
		
		*/
		
		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setPreco(preco);
		
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		em.persist(produto);
		tx.commit();
		JpaUtil.close();
		
		

		return "listar.xhtml";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}


	public Collection<Produto> getProdutos() {
		EntityManager em = JpaUtil.getEntityManager();
		String jpql = "select p from Produto p";
		Query query = em.createQuery(jpql);
		return query.getResultList();
		//return produtos;
	}

	public void setProdutos(Collection<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
	public String excluirProdutoPorId(Produto produto, Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		produto = em.find(Produto.class, id);
		em.remove(produto);
		tx.commit();
		JpaUtil.close();
		return "listar.xhtml";
	}
	
	public void excluir(){
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		produtoSelecionado = em.find(Produto.class, produtoSelecionado.getId());
		em.remove(this.produtoSelecionado);
		tx.commit();
		JpaUtil.close();		
	}
	
	
}
package br.com.senacrs.alp.aulas;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;



public class HerancaTest {
	
	Aluno aluno = Mockito.mock(Aluno.class);
	Cliente cliente = Mockito.mock(Cliente.class);
	Diretor diretor = Mockito.mock(Diretor.class);
	Funcionario funcionario = Mockito.mock(Funcionario.class);
	Pessoa pessoa = Mockito.mock(Pessoa.class);
	Professor professor = Mockito.mock(Professor.class);
	
	@Test
	public void testHieraquiaAluno() {
		
		String msg = null;
		Object obj = null;
	
		obj = aluno;
		msg = "Precisa herdar de Pessoa";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Pessoa.class));
		msg = "Precisa herdar de Cliente";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Cliente.class));
	}

	@Test
	public void testHieraquiaCliente() {	
		
		String msg = null;
		Object obj = null;
		
		obj = cliente;
		msg = "Precisa herdar de Pessoa";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Pessoa.class));
	}

	@Test
	public void testHieraquiaDiretor() {
		
		String msg = null;
		Object obj = null;
		
		obj = diretor;
		msg = "Precisa herdar de Pessoa";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Pessoa.class));
		msg = "Precisa herdar de Funcionario";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Funcionario.class));
	}

	@Test
	public void testHieraquiaFuncionario() {
		
		String msg = null;
		Object obj = null;
		
		obj = funcionario;
		msg = "Precisa herdar de Pessoa";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Pessoa.class));
	}

	@Test
	public void testHieraquiaProfessor() {
		
		String msg = null;
		Object obj = null;
		
		obj = professor;
		msg = "Precisa herdar de Pessoa";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Pessoa.class));
		msg = "Precisa herdar de Funcionario";
		Assert.assertThat(msg, obj, CoreMatchers.instanceOf(Funcionario.class));
	}
}

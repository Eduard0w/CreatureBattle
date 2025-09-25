package model;

import java.util.ArrayList;
import java.util.List;

public class Criatura {
	private String nome; 
	private double hp, atk, def;
	private int velocidade;
	private TipoElemental element;
	private List<Habilidade> habilidades;
	private List<EfeitoStatus> efeitosAtivos;
	private List<Item> inventario;

	public Criatura(String nome, double hp, double atk, double def, int velocidade, TipoElemental element) {
		this.nome = nome;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.velocidade = velocidade;
		this.element = element;
		this.habilidades = new ArrayList<>();
		this.efeitosAtivos = new ArrayList<>();
		this.inventario = new ArrayList<>();
	}

	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;	}
	
	public double getHp() {return hp;}
	public void setHp(double hp) {this.hp = hp;	}
	
	public double getAtk() {return atk;	}
	public void setAtk(double atk) {this.atk = atk;}
	
	public double getDef() {return def;	}
	public void setDef(double def) {this.def = def;	}
	
	public TipoElemental getElement() { return element;}
	public void setElement(TipoElemental element) {this.element = element;	}
	
	public int getVelocidade() {return velocidade;}
	public void setVelocidade(int velocidade) {	this.velocidade = velocidade;}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void adicionarHabilidade(Habilidade habilidade) {
		if (habilidade.getElemento() == this.element) {
			this.habilidades.add(habilidade);
		}
	}

	public List<EfeitoStatus> getEfeitosAtivos() {
		return efeitosAtivos;
	}

	public void adicionarEfeitoStatus(EfeitoStatus efeito) {
		this.efeitosAtivos.add(efeito);
	}

	public void removerEfeitoStatus(EfeitoStatus efeito) {
		this.efeitosAtivos.remove(efeito);
	}

	public List<Item> getInventario() {
		return inventario;
	}

	public void adicionarItem(Item item) {
		this.inventario.add(item);
	}

	public void usarItem(Item item, Criatura alvo) {
		if (inventario.contains(item)) {
			item.usar(alvo);
			inventario.remove(item);
		} else {
			System.out.println(this.nome + " não possui " + item.getNome() + " no inventário.");
		}
	}

	// --------------------------------- \\
	
	public void atacar(Criatura inimigo) {
		System.out.println(this.nome + " Atacou "+ inimigo.nome);
	}
	
	public void receberDano(double dano) {
		if(this.hp <= 0) {
			System.out.println(this.nome+" já está morto!");
			return;
		}
		this.hp -= dano; 
		if (this.hp < 0) this.hp = 0;
		System.out.println(this.nome + " Recebeu: " + String.format("%.2f", dano) + " de dano. HP atual: "+ String.format("%.2f", this.hp));
	}
	
	public void setAtaque(double novoAtaque) {
        this.atk = novoAtaque;
    }
	
	public void curar(int valor) {
		if(this.hp < 100) {
			this.hp += valor;
			System.out.println(nome + " recuperou " + valor + " de HP!");
		}else if(this.hp >= 100){
			System.out.println("Sua vida já está cheia!");
		}
		
    }
	
	public void usarHabilidade(Habilidade habilidade, Criatura alvo) {
		System.out.println(this.nome + " usou "+ habilidade.nomeHabilidade() + " em " + alvo.getNome());
		habilidade.atacarHabilidade(this, alvo);
	}

	public boolean estaVivo() {
		return this.hp > 0;
	}
	
}

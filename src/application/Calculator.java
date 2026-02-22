package application;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
	int larguraJanela = 360;
	int alturaJanela = 540;
	
	Color numeros = new Color(64, 82, 214);
	Color operadores = new Color(6, 35, 89);
	Color fundo = new Color (28, 28, 28);
	Color destaque = new Color (21, 145, 234);
	
	String[] valorBotoes = {
	        "C", "+/-", "%", "÷", 
	        "7", "8", "9", "×", 
	        "4", "5", "6", "-",
	        "1", "2", "3", "+",
	        "√", "0", ".", "="
	    };
		String[] simbolosOperadores = {"÷", "×", "-", "+"};
	    String[] simbolosTopo = {"+/-", "%"};
	    String[] simbolosDestaque = {"C","="};
	
	JFrame janela = new JFrame("Calculadora");
	
	JLabel rotuloDisplay = new JLabel();
	JPanel painelDisplay = new JPanel();
	JLabel rotuloHistorico = new JLabel();
	
	JPanel painelBotoes = new JPanel();
	
	String A = "0";
	String operador = null;
	String B = null;
	boolean operadorRaiz = false;
	
	
	public Calculator(){
		janela.setVisible(true);
		janela.setSize(larguraJanela, alturaJanela);
		janela.setLocationRelativeTo(null);
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new BorderLayout());
		
		rotuloDisplay.setBackground(fundo);
		rotuloDisplay.setForeground(Color.white);
		rotuloDisplay.setFont(new Font("Roboto", Font.PLAIN, 80));
		rotuloDisplay.setHorizontalAlignment(JLabel.RIGHT);
		rotuloDisplay.setText("0");
		rotuloDisplay.setOpaque(true);
		
		rotuloHistorico.setBackground(fundo);
		rotuloHistorico.setForeground(Color.gray);
		rotuloHistorico.setFont(new Font("Roboto", Font.PLAIN, 25));
		rotuloHistorico.setHorizontalAlignment(JLabel.RIGHT);
		rotuloHistorico.setText("");
		rotuloHistorico.setOpaque(true);

		
		
		painelDisplay.setLayout(new BorderLayout());
		painelDisplay.add(rotuloDisplay);
		painelDisplay.add(rotuloHistorico, BorderLayout.NORTH);
		janela.add(painelDisplay, BorderLayout.NORTH);
		
		painelBotoes.setLayout(new GridLayout(5, 4));
		painelBotoes.setBackground(fundo);
		janela.add(painelBotoes);
		
		for (int i = 0; i < valorBotoes.length; i++) {
			JButton botao = new JButton();
			String valorBotao = valorBotoes[i];
			botao.setFont(new Font("Roboto", Font.PLAIN, 30));
			botao.setText(valorBotao);
			botao.setFocusable(false);
			botao.setBorder(new LineBorder(fundo));
			
			if(Arrays.asList(simbolosTopo).contains(valorBotao) || Arrays.asList(simbolosOperadores).contains(valorBotao)) {
				botao.setBackground(operadores);
				botao.setForeground(Color.white);
			}
			else if(Arrays.asList(simbolosDestaque).contains(valorBotao)) {
				botao.setBackground(destaque);
				botao.setForeground(Color.white);
			}
			else {
				botao.setBackground(numeros);
				botao.setForeground(Color.white);
			}
			painelBotoes.add(botao);
			
			botao.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JButton botao = (JButton) e.getSource();
					String valorBotao = botao.getText();
					
					if ("√".contains(valorBotao)) {
					    operadorRaiz = true;
					    rotuloDisplay.setText(valorBotao);
					    B = "0";
					}					
					else if(Arrays.asList(simbolosTopo).contains(valorBotao)) {
						if (valorBotao == "+/-") {
							double numeroDisplay = Double.parseDouble(rotuloDisplay.getText());
							numeroDisplay *= -1;
							rotuloDisplay.setText(removerZeroDecimal(numeroDisplay));
						}
						else if (valorBotao == "%"){
							double numeroDisplay = Double.parseDouble(rotuloDisplay.getText());
							numeroDisplay /= 100;
							rotuloDisplay.setText(removerZeroDecimal(numeroDisplay));
						}
					}
					else if(Arrays.asList(simbolosOperadores).contains(valorBotao)) {
					    if (operadorRaiz) {
					        rotuloDisplay.setText(rotuloDisplay.getText() + valorBotao);
					    }
					    else if (operador == null) {
					        A = rotuloDisplay.getText();
					        rotuloHistorico.setText(A + valorBotao);
					        rotuloDisplay.setText("0");
					        B = "0";
					    }
					    operador = valorBotao;
					}
					else if(Arrays.asList(simbolosDestaque).contains(valorBotao)) {
						if (valorBotao == "C") {
							apagarTudo();
							rotuloDisplay.setText("0");
							rotuloHistorico.setText("");
						}
						else if (valorBotao == "=") {
							if (operadorRaiz) {
							    String textoDisplay = rotuloDisplay.getText();
							    if (textoDisplay.contains("-") || textoDisplay.contains("+") || textoDisplay.contains("÷") || textoDisplay.contains("×")) {
							        rotuloDisplay.setText("Erro");
							    } else {
							        double numeroRaiz = Double.parseDouble(textoDisplay.replace("√", ""));
							        if (numeroRaiz < 0) {
							            rotuloDisplay.setText("Erro");
							        } else {
							            rotuloDisplay.setText(removerZeroDecimal(Math.sqrt(numeroRaiz)));
							        }
							    }
							    apagarTudo();
							}
							else if (A != null) {
								B = rotuloDisplay.getText();
								double numA = Double.parseDouble(A);
								double numB = Double.parseDouble(B);
								rotuloHistorico.setText(A + operador + B + "=");
								
								if (operador == "+") {
									rotuloDisplay.setText(removerZeroDecimal(numA + numB));
								}
								else if (operador == "-") {
									rotuloDisplay.setText(removerZeroDecimal(numA - numB));
								}
								else if (operador == "÷") {
									rotuloDisplay.setText(removerZeroDecimal(numA / numB));
								}
								else if (operador == "×") {
									rotuloDisplay.setText(removerZeroDecimal(numA * numB));
								}
								apagarTudo();
							}
						}
					}
					else {
						if (valorBotao == ".") {
							if(!rotuloDisplay.getText().contains(valorBotao)) {
								rotuloDisplay.setText(rotuloDisplay.getText() + valorBotao);
							}
						}
						else if ("0123456789".contains(valorBotao)) {
							if (rotuloDisplay.getText() == "0") {
								rotuloDisplay.setText(valorBotao);
							}
							else {
								rotuloDisplay.setText(rotuloDisplay.getText() + valorBotao);
							}
						}
					}
				}
			});
		}	
	}
	
	void apagarTudo() {
		A = "0";
		operador = null;
		B = null;
		operadorRaiz = false;
	}
	
	String removerZeroDecimal(double numeroDisplay) {
		if (numeroDisplay % 1 == 0) {
			return Integer.toString((int) numeroDisplay);		
		}
		else {
			return Double.toString(numeroDisplay);
		}
	}
}

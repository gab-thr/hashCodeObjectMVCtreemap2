package Controller;

import java.util.List;

import Model.Line;
import Model.Model;
import Model.Paint;
import View.View;

import java.util.TreeMap;
import java.util.Set;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Controller {
	private Model model;
	private View view;
	
	
	
	public Controller(Model model,View view) {
		this.model = model;
		this.view = view;
	}

	public static boolean compare(char[][] tab, char[][]tab2) {
    	for (int i=0; i<tab.length;i++) {
    		for (int j=0; j<tab[0].length;j++) {
    			if (tab[i][j]!=tab2[i][j]) {
    				System.out.println("Erreur à:" + i + "|"+ j);
    				return false;
    			}
    		}
    	}
		return true;
    }
    
	public static void findSolutionLine(char[][] tab, List<Paint> cmds) {
		for (int row = 0; row<tab.length;row++) {
			findSolutionOneLine(tab[row], row, cmds);
		}
	}
	
	public static void findSolutionOneLine(char[] line, int numLine, List<Paint> cmds) {
		int i=0;
		boolean block = false;
		int startBlock = 0;
		int lenBlock =0;

		while(i<line.length) {
			if(line[i]==Paint.PAINT) {
				if(block == false) {
					block=true;
					startBlock = i;
					lenBlock=0;
				}
				else {
					lenBlock++;
					if (lenBlock > Model.lenMax) {
						Model.lenMax = lenBlock;
					}
				}
			}else {
				if(block == true) {
					cmds.add(new Line(numLine, startBlock, numLine, startBlock + lenBlock, lenBlock));

				}
				block = false;
			}
			
			i++;			
		}
		
		// Si un bloc touche la fin de la line
		if(block == true) {
			cmds.add(new Line(numLine, startBlock, numLine, startBlock + lenBlock, lenBlock));
		}
	}
	
	public static void findSolutionColumn(char[][] tab, List<Paint> cmds) {
		for (int column = 0; column<tab[0].length;column++) {
			findSolutionOneColumn(tab, column, cmds);
		}
	}

	private static void findSolutionOneColumn(char[][] tab, int numColumn, List<Paint> cmds) {
		int i = 0;
		boolean block = false;
		int startBlock = 0;
		int lenBlock=0;
		
		while (i<tab.length) {
			if (tab[i][numColumn] == Paint.PAINT) {
				if (block == false) {
					block = true;
					startBlock = i;
					lenBlock = 0;
				}
				else {
					lenBlock++;
					if (lenBlock > Model.lenMax) {
						Model.lenMax = lenBlock;
					}
				}
			} 
			else {
				if(block == true) {
					cmds.add(new Line(startBlock, numColumn, startBlock + lenBlock, numColumn, lenBlock));
				}
				block = false;
			}	
			i++;
		}
		
		// Si un bloc touche la fin de la colonne
		if(block == true) { 
			cmds.add(new Line(startBlock, numColumn, startBlock + lenBlock, numColumn, lenBlock));
		}
	}

	public static void findFinalSolution(char[][] tabIn, char[][] midTab, char[][] tabOut, List<Paint> cmds) {
		
		findSolutionLine(tabIn, cmds);
		findSolutionColumn(tabIn, cmds);
		Paint p;
		while( null != (p = getHeavy(cmds)) ) {
			paintTab(tabOut,p);
			eraseP(midTab,p);
			updateWeight(cmds, midTab);
			View.printTab(tabOut);
//			View.printTab(midTab);
			System.out.println("cmds size= " + cmds.size());
//			System.out.println("===================================================================================");			
		}
		
		
	}
	
	private static void updateWeight(List<Paint> cmds, char[][] midTab) {
		for (int i=cmds.size()-1 ; i>=0 ; i--) {
			Paint p = cmds.get(i);
			p.updateWeight(midTab);
			if(p.getWeight()==0) {
				cmds.remove(i);
			}
		}
	}

	public static Paint getHeavy(List<Paint> cmds) {
		if(cmds.isEmpty()) {
			return null;
		}
		Paint res = cmds.get(0);
		
		for(Paint p: cmds) {
			if(p.getWeight() > res.getWeight()) {
				res = p;
			}
		}
		
		cmds.remove(res);
		return res;
	}
		
	public static void paintTab(char[][] tab, Paint paint) {
		Paint p = paint; 
		p.paint(tab);	
		Model.nbCoups++;
	}

	public static void eraseP(char[][] tab, Paint paint) {
		Paint p = paint;
			p.erase(tab);
		
	}
		
}

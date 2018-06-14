package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.util.TreeMap;

import javax.swing.JButton;

import java.util.Set;
import java.util.Iterator;
import java.util.Map;

public class Model {
	
	private static List<Paint> cmd;
	private char[][] tab;
	
	private int nbLine=0;
	private int nbColumn=0;
	public static int lenMax = 0;
	public static int max = 0;
	
	public static int nbCoups = 0;
	
	public Model(String fileName) {
		cmd = new ArrayList<Paint>();		
		tab = load(fileName);
	}
	
	public Model(int line, int column) {
		cmd = new ArrayList<Paint>();
		this.nbLine=line;
		this.nbColumn=column;
		tab = new char[line][column];
		initTab(tab);
		findLongest(line, column);
	}

	public int getNbLine() {
		return nbLine;
	}
	public int getNbColumn() {
		return nbColumn;
	}
	public char[][] getTab() {
		return tab;
	}
	public static List<Paint> getCommande() {
		return cmd;
	}
	
	private void findLongest(int line, int column) {
		if (line > column) {
			max = line;
		}
		else {
			max = column;
		}
	}
	
public static char[][] load(String fileName) {
        char[][] res=null;
        
        try
        {     
               InputStream ips=new FileInputStream(fileName);
               InputStreamReader ipsr=new InputStreamReader(ips);
               BufferedReader br=new BufferedReader(ipsr);
               String ligne;
               ligne=br.readLine();
               if(ligne != null)
               {
                      String[] xy = ligne.split(" ");
                      int nbLigne = Integer.parseInt(xy[0]);
                      int nbColonne = Integer.parseInt(xy[1]);
                      res = new char[nbLigne][nbColonne];
                     
                      int m = 0;
                      while ((ligne=br.readLine())!=null){
                             for(int i=0 ; i<nbColonne ; i++)
                             {
                                   res[m][i] = ligne.charAt(i);
                             }
                             m++;
                      }
               }

               br.close();
        }            
        catch (Exception e){
               System.out.println(e.toString());
        }
       
        return res;
	} 
	
	private void initTab(char[][] tab2) {
		for(int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				tab[i][j] = Paint.BLANK;
			}
		}
	}
	
}

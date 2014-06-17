package com.example.goandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import enumeration.Couleur;
import enumeration.PasseOuJoue;

import android.content.Context;
import android.util.Log;
import structure.ActionJoueur;
import structure.ActionRealiseeStruct;
import structure.Pion;

public class SauvegardePartie {
	
	private MainActivity mainActivity;
	private File file;
	private byte[] bufferOut;
	private FileInputStream  inPut;
	private FileOutputStream output;
	private ActionRealiseeStruct inActionRealisees;
	/*************************************************************************/
	/*************************************************************************/
	public SauvegardePartie(MainActivity inMain){	
		this.mainActivity = inMain;	
		this.file = new File("SauvegardePartie.txt");
	}
	
	/*************************************************************************/
	/*************************************************************************/
	public void EnregistrerLaPartie(ActionRealiseeStruct inActionRealisees){			
		/******************************************************************/
		/*				Declaration des variables 				   		  */
		/******************************************************************/
		String dataNombreDElement;
		String trame;
		String dataPasseOuJouer;
		String dataSaveCouleur;
		String dataSavePionX;
		String dataSavePionY;
		
		/******************************************************************/
		/*				Declaration des variables 				   		  */
		/******************************************************************/
		try
		{		
				//Enregistrement du nombre des actions réalisées
				this.output = this.mainActivity.openFileOutput(this.file.getName(), 
	                    									   Context.MODE_APPEND);			
				dataNombreDElement = Integer.toString(this.mainActivity.plateau.taille);
				trame = ">:" + dataNombreDElement + ";";				
				this.bufferOut = null;
				this.bufferOut = new byte[trame.length()];
				this.bufferOut = trame.getBytes();
				this.output.write(this.bufferOut);					
				this.closeOutPutStream();
				
				//Enregistrement du nombre des actions réalisées
				this.output = this.mainActivity.openFileOutput(this.file.getName(), 
	                    									   Context.MODE_APPEND);			
				dataNombreDElement = Integer.toString(inActionRealisees.nbrPositionsActuel);
				trame = ">:" + dataNombreDElement + ";";				
				this.bufferOut = null;
				this.bufferOut = new byte[trame.length()];
				this.bufferOut = trame.getBytes();
				this.output.write(this.bufferOut);					
				this.closeOutPutStream();	
				
				//Enregistrement des information de chaque action
				trame = "";		
			    for(int i = 0; i < inActionRealisees.nbrPositionsActuel; i++)
			    {			    	
			    	this.output = this.mainActivity.openFileOutput(this.file.getName(), 
			    												   Context.MODE_APPEND);    	
					if(output != null)
				    {
						//Données Couleur
						dataSaveCouleur 		= "0" + Integer.toString(inActionRealisees.lesActions.get(i).pion.couleur.ordinal());
						
						//Données PionX						
						if(inActionRealisees.lesActions.get(i).pion.position.x > 9)
						{
							dataSavePionX 		= Integer.toString(inActionRealisees.lesActions.get(i).pion.position.x);							
						}
						else
						{
							dataSavePionX 		= "0" + Integer.toString(inActionRealisees.lesActions.get(i).pion.position.x);
						}

						//Données PionY					
						if(inActionRealisees.lesActions.get(i).pion.position.y > 9)
						{
							dataSavePionY 		= Integer.toString(inActionRealisees.lesActions.get(i).pion.position.y);						
						}
						else
						{
							dataSavePionY 		= "0" + Integer.toString(inActionRealisees.lesActions.get(i).pion.position.y);
						}
						
						//Données PasseOuJouer
						dataPasseOuJouer		= "0" + Integer.toString(inActionRealisees.lesActions.get(i).passeeOuJouee.ordinal());
						
						//Construction des trames
						trame			=	">P:"  + dataPasseOuJouer +
											",C:" + dataSaveCouleur  +
											",X:" + dataSavePionX	 +
											",Y:" + dataSavePionY	 + 
											";";
						this.bufferOut = null;
						this.bufferOut = new byte[trame.length()];
						this.bufferOut = trame.getBytes();
						this.output.write(this.bufferOut);	
						this.closeOutPutStream();
			    	}			  				
			    }			    			   
		}	
		catch(IOException exception)
		{								      
		}
	}
	
	/*************************************************************************/
	/*************************************************************************/
	public void creationFichier()
	{	
		try{
			this.output = this.mainActivity.openFileOutput(this.file.getName(), 
														   Context.MODE_PRIVATE);		
			this.closeOutPutStream();
		}
		catch(IOException exception)
		{								      
		}	
	}
	
	/*************************************************************************/
	/*************************************************************************/
	public ActionRealiseeStruct lecture()
	{	
		/******************************************************************/
		/*				Declaration des variables 				   		  */
		/******************************************************************/			
		boolean finTrame = true;
		int 	dataNbrActionsRealisees  = 0;
		int		dataPionX 	 = 0;
		int 	dataPionY 	 = 0;
		int		dataCouleur  = 0;
		int 	dataPasseOuJoue = 0;
		int 	iterrtion	 = 0;
		int 	iterrData	 = 0;
		char 	data		 = 0;
		char[]  inputBuffer  = new char[65000];
		String  pointVergule = "";
		Pion 	pionDeReference; 		
		
		this.inActionRealisees = new ActionRealiseeStruct();
		/******************************************************************/
		/*				Declaration des variables 				   		  */
		/******************************************************************/
		try{
			// Ouvrir le fichier en mode lecture
			this.inPut = this.mainActivity.openFileInput(this.file.getName());
			if(this.inPut != null){
				InputStreamReader isr = null; 
				isr = new InputStreamReader(this.inPut);
				// Lire tous le contenu du fichier
				isr.read(inputBuffer);									
			}			
			this.closeInPutStream();
			
			//Récupération des nombres
			iterrtion 	 = 6;
			dataNbrActionsRealisees = Character.getNumericValue(inputBuffer[iterrtion]);
			iterrtion++;
			finTrame 	 = true;
			pointVergule = ";";
									
			while(finTrame){
				data = inputBuffer[iterrtion];
				if(data != pointVergule.charAt(0))
				{
					dataNbrActionsRealisees = dataNbrActionsRealisees * 10 + Character.getNumericValue(data);										
				}
				else
				{
					finTrame = false;
				}
				iterrtion++;
			}
			this.inActionRealisees.nbrPositionsActuel = dataNbrActionsRealisees;
		
			for(iterrData = 11; iterrData < this.inActionRealisees.nbrPositionsActuel*21; iterrData += 21 )
			{
				dataPasseOuJoue = Character.getNumericValue(inputBuffer[iterrData]);
				dataPasseOuJoue = dataPasseOuJoue * 10 + Character.getNumericValue(inputBuffer[iterrData + 1]);
				
				dataCouleur = Character.getNumericValue(inputBuffer[iterrData + 5]);
				dataCouleur = dataCouleur * 10 + Character.getNumericValue(inputBuffer[iterrData + 6]); 
						
				dataPionX 	= Character.getNumericValue(inputBuffer[iterrData + 10]);
				dataPionX   = dataPionX * 10 + Character.getNumericValue(inputBuffer[iterrData + 11]);
						
				dataPionY 	= Character.getNumericValue(inputBuffer[iterrData + 15]);
				dataPionY 	= dataPionY * 10 + Character.getNumericValue(inputBuffer[iterrData + 16]);
				
				pionDeReference = null;
				pionDeReference = new Pion();
				pionDeReference.couleur 	=  Couleur.values()[dataCouleur];
				pionDeReference.position.x 	=  dataPionX;
				pionDeReference.position.y	=  dataPionY;
				
				this.inActionRealisees.lesActions.add(new ActionJoueur(pionDeReference , PasseOuJoue.values()[dataPasseOuJoue]));				
			}					
		}
		catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
		catch(IOException exception)
		{								      
		}	
		
		return this.inActionRealisees;
	}
	
	/*************************************************************************/
	/*************************************************************************/
	public int lireLaTaille(){
		char[]  inputBuffer  = new char[4];
		try{
			// Ouvrir le fichier en mode lecture
			this.inPut = this.mainActivity.openFileInput(this.file.getName());
			if(this.inPut != null){
				InputStreamReader isr = null; 
				isr = new InputStreamReader(this.inPut);
				// Lire tous le contenu du fichier
				isr.read(inputBuffer);									
			}			
			this.closeInPutStream();
									
		}
		catch (FileNotFoundException e) {
	        System.out.println("File Not Found.");
	        e.printStackTrace();
	    }
		catch(IOException exception)
		{								      
		}	
		
		return Character.getNumericValue(inputBuffer[2]);
	}	
	
	/*************************************************************************/
	/*************************************************************************/
	public void recuperationListeDeFichier(){	
		try{
			
			String [] listefichiers = this.mainActivity.fileList();
			
			for(int i = 0; i < listefichiers.length; i++){
				
				if(listefichiers[i].endsWith(".txt")==true)
				{
					Log.d("LISTE_SAUVEGARDE", listefichiers[i]);
				}				
			}
		}
		catch(Exception exception)
		{								      
		}
	}
	
	/*************************************************************************/
	/*************************************************************************/
	public void closeOutPutStream(){	
		try{
			if(this.output != null){
				this.output.close();
		    }
		}
		catch(IOException exception)
		{								      
		}
	}
	
	/*************************************************************************/
	/*************************************************************************/
	public void closeInPutStream(){	
		try{
			if(this.inPut != null){	
				this.inPut.close();
			}
		}
		catch(IOException exception)
		{								      
		}
	}
	
}//fin de la classe

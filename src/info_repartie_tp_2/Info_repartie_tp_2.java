/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info_repartie_tp_2;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Yohan Sanchez (yohan.sanchez34@gmail.com)
 */
public class Info_repartie_tp_2 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
	}

	int nbLecteurs = 0;
	Semaphore mutexLecteur = new Semaphore(1);
	Semaphore mutexRedacteur = new Semaphore(1);
	Semaphore mutexModeAcces = new Semaphore(1);
	void demandeDeLecture() throws InterruptedException {
	mutexLecteur.acquire();/* bloque les autres lecteurs */
		nbLecteurs++;
		if (nbLecteurs == 1) /* premier lecteur bloque */ {
			mutexModeAcces.acquire();/* le système en mode lecture */
		}
	mutexLecteur.release();/* débloque les autres lecteurs */
	}

	void finDeLecture() throws InterruptedException {
		mutexLecteur.acquire();/* bloque les autres lecteurs */
		nbLecteurs--;
		if (nbLecteurs == 0) /* dernier lecteur débloque */ {
			mutexModeAcces.release();/* le système du mode lecture */
		}
		mutexLecteur.release();/* débloque les autres lecteurs */
	}

	void demandeDEcriture() throws InterruptedException {
		mutexRedacteur.acquire(); /* bloque les autres rédacteurs */
		mutexModeAcces.acquire(); /* bloque en mode écriture */
	}

	void finDEcriture() {
		mutexModeAcces.release();/* débloque le mode écriture */
		mutexRedacteur.release(); /* débloque les autres rédacteurs */
	}


	void lecteur() throws InterruptedException {
		while (true) {
			demandeDeLecture();
			//lectureDesDonnées();
			finDeLecture();
		}
	}

	void redacteur() throws InterruptedException {
		while (true) {
			demandeDEcriture();
			//ecritureDesDonnées();
			finDEcriture();
		}
	}
}

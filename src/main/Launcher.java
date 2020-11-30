/* Names:
 * Eric Lin(33%), Michael Li(33%), Zain Muhammad(33%)
 * Eric Lin - GUI
 * Michael Li - logic & management
 * Zain Muhammad - classes/objects
 * 
 * Date:
 * November 29, 2020
 * 
 * Course Code:
 * ICS4U1-51, Mr. Fernandes
 * 
 * Title:
 * Amazing Labyrinth (project title)
 * The Amazing Labyrinth (product name)
 * 
 * Description:
 * Our project took the ever popular Amazing Labyrinth board game and recreated it to be played on 
 * the computer with some slight touches and changes.
 * 
 * Features:
 *  	- potential pathways a player can take are highlighted
 *      - Game can be saved and reloaded 
 *      - player movement is animated and not directly placed
 *      - smooth rotation of tiles
 *      - 
 * 
 * Major Skills:
 * 		- knowledge of graphical user interfaces 
 *  	- basic graph theory/graph traversal algorithms 
 *  	- Object oriented programming practices (packages, classes)
 *      - Polymorphism and inheritance 
 *      - file access and creation
 *      - 
 * 
 * Areas of Concern: 
 * 		- the size of our game is roughly 10mb, and should be able to run on almost all systems thanks
 *        to the low system requirements to run the game.
 *      - 
 * 
 */
package main;
import guiClasses.StartScreenGUI;

public class Launcher {
    public static void main(String[] args) {
        new StartScreenGUI();
    }
}

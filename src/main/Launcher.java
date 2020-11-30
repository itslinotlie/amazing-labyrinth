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

/**
 * Group Members:
 * (35%) Eric Lin | GUI & Debugging
 * (35%) Michael Li | Logic & Git
 * (30%) Zain Muhammad | Objects & Visuals
 *
 * Date: 11/29/20
 *
 * Course Code: ICS4U
 *
 * Instructor Name: Mr. Fernandes
 *
 * Project Title: Amazing Labyrinth
 * Product Name: The Amazing Labyrinth
 *
 * Description: Recreating the board game Amazing Labyrinth
 * with some personalized touches and changes
 *
 * Features:
 *   -Traversable pathways are highlighted for the current player
 *   -Game is automatically saved and can be reloaded anytime
 *   -Player movements are animated
 *   -Very light in terms of file size (~1MB) excluding git history
 *
 * Major Skills:
 *   -Graphical user interfaces
 *   -Graph traversal algorithms
 *   -OOP practices (i.e. polymorphism and inheritance)
 *   -File writing and accessing
 *   -The use of Git as a version control system
 *
 * Areas of Concern:
 *   -Some tile images are not aligned with others, making the board seem “off”
 *   -Players cannot be fully customizable (i.e. player name, icon etc.)
 */
public class Launcher {
    public static void main(String[] args) {
        new StartScreenGUI();
    }
}

/*
 * Aineopintojen harjoitustyö: Tietorakenteet ja algoritmit
 * Helsingin yliopisto Tietojenkäsittelytieteen laitos
 * Ooppa 2015 - GNU General Public License, version 3.
 */
package domain;

import java.util.HashMap;

/**
 * Used to separate certain items from each other.
 *
 * @author Ooppa
 * @param <E>
 */
public class Category<E> extends AbstractElement {

    private HashMap<Long, AbstractElement> elements;

    public Category(long id, String name) {
        super(id, name);
        this.elements = new HashMap<>();
    }
    
    /**
     * Returns a list of categories associated with the category
     *
     * @return elements a list of elements currently in this category
     */
    public HashMap<Long, AbstractElement> getElements() {
        return elements;
    }

    /**
     * Add a new element to this category
     *
     * @param element element to add
     */
    public void addElement(AbstractElement element) {
        if(this.elements.containsValue(element) == false && element != null){
            this.elements.put(element.getId(), element);
        }
    }

    /**
     * Remove the element given from the category
     *
     * @param element element to remove
     */
    public void removeElement(AbstractElement element) {
        if(this.elements.containsValue(element)){
            this.elements.remove(element.getId(), element);
        }
    }

    @Override
    public String toString() {
        return this.getId() + ": "+ this.getName();
    }
    
    

}

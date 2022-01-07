import java.util.ArrayList;
import java.util.List;



public class OrderedGeneralTree<E> extends AbstractTree<E>{

    protected static class Node<E> implements Position<E> {
        private E element;          // an element stored at this node
        private Node<E> parent;     // a reference to the parent node (if any)
        private Node<E> firstChild;       // a reference to the  firstChild (if any)
        private Node<E> nextSibling;      // a reference to the nextSibling (if any)
    
        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e  the element to be stored
         * @param above       reference to a parent node
         * @param firstChild   reference to a first child node
         * @param nextSibling  reference to a next sibling node
         */
        public Node(E e, Node<E> above, Node<E> firstChild, Node<E> nextSibling) {
          element = e;
          parent = above;
          this.firstChild = firstChild;
          this.nextSibling = nextSibling;
        }
        
    
        // accessor methods
        public E getElement() { return element; }
        public Node<E> getParent() { return parent; }
        public Node<E> getFirstChild() { return firstChild; }
        public Node<E> getNextSibling() { return nextSibling; }
    
        // update methods
        public void setElement(E e) { element = e; }
        public void setParent(Node<E> parentNode) { parent = parentNode; }
        public void setFirstChild(Node<E> firstChild) { this.firstChild = firstChild; }
        public void setNextSibling(Node<E> nextSibling) { this.nextSibling = nextSibling; }
    } //----------- end of nested Node class -----------
    
      /** Factory function to create a new node storing element e. */
      protected Node<E> createNode(E e, Node<E> parent,
                                      Node<E> firstChild, Node<E> nextSibling) {
        return new Node<E>(e, parent, firstChild, nextSibling);
      }
    
      // OrderedGeneralTree instance variables
      /** The root of the OrderedGeneralTree */
      protected Node<E> root = null;     // root of the tree
    
      /** The number of nodes in the OrderedGeneralTree */
      private int size = 0;              // number of nodes in the tree
    
      // constructor
      /** Construts an empty  OrderedGeneralTree. */
      public OrderedGeneralTree() { }      // constructs an empty OrderedGeneralTree
    
    
      /**
       * Returns the number of nodes in the tree.
       * @return number of nodes in the tree
       */
      @Override
      public int size() {
        return size;
      }
    



      /**
       * Returns the root Position of the tree (or null if tree is empty).
       * @return root Position of the tree (or null if tree is empty)
       */
      @Override
      public Position<E> root() {
        return root;
      }
    
      
    

      /**
       * Places element e at the root of an empty tree and returns its new Position.
       *
       * @param e   the new element
       * @return the Position of the new element
       * @throws IllegalStateException if the tree is not empty
       */

    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
        List<Position<E>> snapshot = new ArrayList<>();
        Position<E> tmp=firstChild(p);
        
        if(tmp!=null) {
            while(tmp!=null) {
                snapshot.add(tmp);
                tmp=nextSibling(tmp);
            }
            
        }
        return snapshot;
    }





    /**
       * Returns the Position of p's parent (or null if p is root).
       *
       * @param p    A valid Position within the tree
       * @return Position of p's parent (or null if p is root)
       * @throws IllegalArgumentException if p is not a valid Position for this tree.
       */
      @Override
      public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
      }



    /**
    * Returns the Position of p's first child (or null if no child exists).
    *
    * @param p A valid Position within the tree
    * @return the Position of the first child (or null if no child exists)
    * @throws IllegalArgumentException if p is not a valid Position for this tree
    */

    public Position<E> firstChild(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getFirstChild();
    }
    


    /**
     * Returns the Position of p's next sibling (or null if no sibling exists).
     *
    * @param p A valid Position within the tree
    * @return the Position of the next sibling (or null if no next sibling exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
      
    public Position<E> nextSibling(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getNextSibling();
    }
    
    
    /**
       * Places element e at the root of an empty tree and returns its new Position.
       *
       * @param p   the any position of node
       * @return the count of the e's children
       * @throws IllegalStateException if p is not a valid Position for this tree.
       */
      @Override
      public int numChildren(Position<E> p) throws IllegalArgumentException {
          if (firstChild(p) != null) return 1+siblingCount(firstChild(p));
          else return 0;
      }


      /**
       * if p is valid it returns its new Position.
       * @param p   the any position of node
       * @return the count of the siblings
       * @throws IllegalStateException if p is not a valid position for this tree.
       */
      public int siblingCount(Position<E> p) throws IllegalArgumentException {
          if(nextSibling(p)!=null) {
              return 1 + siblingCount(nextSibling(p));
          }
          else return 0;
      }





      // nonpublic utility
    /**
    * Verifies that a Position belongs to the appropriate class, and is
    * not one that has been previously removed. Note that our current
    * implementation does not actually verify that the position belongs
    * to this particular list instance.
    *
    * @param p   a Position (that should belong to this tree)
    * @return    the underlying Node instance for the position
    * @throws IllegalArgumentException if an invalid position is detected
    */
    
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
          throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;       // safe cast
        if (node.getParent() == node)     // our convention for defunct node
          throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    } 



     // update methods supported by this class
      
      /**
       * Places element e at the root of an empty tree and returns its new Position.
       *
       * @param e   the new element
       * @return the Position of the new element
       * @throws IllegalStateException if the tree is not empty
       */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }
      

    /**
       * Places element e to the node by given position p and returns its new Position.
       *
       * @param e   the new element
       * @return the Position of the new element
       * @throws IllegalStateException if the p is not valid (null).
       */
    
    public Position<E> addChild(Position<E> p, E e) {
        Node<E> parent = validate(p);
        Node<E> child=null;
        Node<E> lastSibling=null;
        Position<E> tmp = parent.getFirstChild();

        if ( tmp == null) {
            child = createNode(e, parent, null, null);
            parent.setFirstChild(child);
        }
        else {
            while(tmp!=null) {
                if(nextSibling(tmp)==null) {
                    lastSibling=validate(tmp);
                    break;
                }
                tmp=nextSibling(tmp);
            }
            child=createNode(e, parent, null, null);
            lastSibling.setNextSibling(child);
        }
        size++;
        return p;

    }


    /**
   * Removes the node at Position p and replaces it with its child, if any.
   *
   * @param p   the relevant Position
   * @return element that was removed
   */
    public E remove(Position<E> p) {
        Node<E> node = validate(p);
        Node<E> parent = node.parent;

        // Used in removing internal nodes.
        Node<E> lastSibling=null;  
        Node<E> lastSibling2=null;

        Position<E> tmp = parent.firstChild;
        Position<E> tmp2 = node.firstChild; // Used in removing internal nodes.

        if(numChildren(p)==0) {
            if(parent.firstChild==node) parent.setFirstChild(node.nextSibling);
            else {
                while(tmp!=node) {
                    if(nextSibling(tmp)==node) {
                        lastSibling=validate(tmp);
                        lastSibling.setNextSibling(node.nextSibling);
                        break;
                    }
                    tmp=nextSibling(tmp);
                }
            }
        }
        else {
            if(parent.firstChild==node) {
                parent.setFirstChild(node.firstChild);
                while(true) {
                    lastSibling=validate(tmp2);
                    lastSibling.setParent(node.parent);
                    if(nextSibling(tmp2)==null) {
                        lastSibling.setNextSibling(node.nextSibling);
                        break;
                    }
                    tmp2=nextSibling(tmp2);
                }
            }
            else {
                while(tmp!=node) {
                    if(nextSibling(tmp)==node) {
                        lastSibling=validate(tmp);
                        lastSibling.setNextSibling(node.firstChild);
                        break;
                    }
                    tmp=nextSibling(tmp);
                }
                while(tmp2!=null) {
                    lastSibling2=validate(tmp2);
                    lastSibling2.setParent(node.parent);
                    if(nextSibling(tmp2)==null) {
                        lastSibling2.setNextSibling(node.nextSibling);
                        break;
                    }
                    tmp2=nextSibling(tmp2);
                }
            }
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);                // help garbage collection
        node.setFirstChild(null);
        node.setNextSibling(null);
        node.setParent(node);                 // our convention for defunct node
        return temp;
    }
    


    /**
   *  printing nodes according to their order in an preorder traversal, one node per line.
   *  The element for a node  preceded by depth number of dots.
   */
    public void displayTree() {
        for(Position<E> p : preorder() ) {
            String s="";
            int depth=depth(p);
            for(int i = 0; i<depth;i++) s+=". ";
            System.out.println(s+p.getElement());
        }
    }

    
}

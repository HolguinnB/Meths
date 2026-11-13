/*Método 1 y 2 (van dentro de BinaryTree<T>)

Agrega este campo junto a list:*/


// Campo auxiliar del método 2: guarda la mejor suma entre dos hojas encontrada hasta el momento
private Integer maxSum;





// MÉTODO 1: ANCESTRO COMÚN MÁS BAJO
public TreeNode<T> lowestCommonAncestor(T a, T b){
    return lowestCommonAncestorImpl(this.root, a, b);
}

private TreeNode<T> lowestCommonAncestorImpl(TreeNode<T> current, T a, T b){
    // Caso base: llegamos al final de una rama sin encontrar nada
    if(current == null){
        return null;
    }

    // Si el nodo actual es a o b, lo devolvemos y NO seguimos bajando:
    // si el otro valor está debajo, este nodo es el ancestro (un nodo es descendiente de sí mismo)
    if(comparator.compare(current.getInfo(), a) == 0 || comparator.compare(current.getInfo(), b) == 0){
        return current;
    }

    // Buscamos a y b en cada subárbol (un único recorrido)
    TreeNode<T> left = lowestCommonAncestorImpl(current.getLeft(), a, b);
    TreeNode<T> right = lowestCommonAncestorImpl(current.getRight(), a, b);

    // Si cada lado encontró uno de los valores, el nodo actual es el ancestro común más bajo
    if(left != null && right != null){
        return current;
    }

    // Si solo un lado encontró algo, subimos ese resultado
    return left != null ? left : right;
}




// MÉTODO 2: SUMA MÁXIMA ENTRE DOS HOJAS (árbol de Integer)
public Integer maxPathSumBetweenLeaves(){
    this.maxSum = null;            // reiniciamos el resultado
    maxPathSumImpl(this.root);     // un solo recorrido en posorden
    return this.maxSum;            // null si el árbol no tiene dos hojas
}

// Devuelve la mayor suma de un camino que baja desde "current" hasta UNA hoja.
// En cada nodo con dos hijos actualiza maxSum con el mejor camino hoja-nodo-hoja.
private Integer maxPathSumImpl(TreeNode<T> current){
    // Caso base: no hay nodo, no hay camino hacia ninguna hoja
    if(current == null){
        return null;
    }

    int value = (Integer) current.getInfo();

    // Una hoja es el final de un camino: su suma es su propio valor
    if(isLeaf(current)){
        return value;
    }

    // Primero resolvemos los hijos (posorden)
    Integer left = maxPathSumImpl(current.getLeft());
    Integer right = maxPathSumImpl(current.getRight());

    // Con dos hijos, el camino sube desde una hoja, pasa por este nodo y baja a otra hoja
    if(left != null && right != null){
        int candidate = left + right + value;
        if(this.maxSum == null || candidate > this.maxSum){
            this.maxSum = candidate;
        }
        // Hacia el padre solo puede subir UNA rama: la mejor
        return value + Math.max(left, right);
    }

    // Un solo hijo: el camino continúa por el hijo que existe (no cuenta como candidato)
    return value + (left != null ? left : right);
}





/*Método 3 (árbol n-ario)

Como el proyecto no tiene nodo n-ario, necesitas esta clase:*/

import java.util.ArrayList;

public class NaryTreeNode <T>{

    private T info;
    private ArrayList<NaryTreeNode<T>> children;

    public NaryTreeNode(T info){
        this.info = info;
        this.children = new ArrayList<>();
    }

    public T getInfo() { return info; }
    public void setInfo(T info) { this.info = info; }
    public ArrayList<NaryTreeNode<T>> getChildren() { return children; }
    public void addChild(NaryTreeNode<T> child){ this.children.add(child); }
}


/*Y en tu clase del árbol n-ario (con un atributo root de tipo NaryTreeNode<T>), agrega el campo y los métodos:*/

// Campo auxiliar: guarda el mayor diámetro encontrado hasta el momento
private Integer diameter;


// MÉTODO 3: DIÁMETRO DE UN ÁRBOL N-ARIO
public Integer diameterTree(){
    this.diameter = 0;                  // reiniciamos el resultado
    if(root == null){
        return this.diameter;
    }
    diameterImpl(this.root);            // un solo recorrido en posorden
    return this.diameter;
}

// Devuelve la altura de "current" en aristas (una hoja vale 0).
// En cada nodo combina sus dos ramas más profundas para actualizar el diámetro.
private Integer diameterImpl(NaryTreeNode<T> current){
    int first = 0;      // rama más profunda que baja desde este nodo
    int second = 0;     // segunda rama más profunda

    for(NaryTreeNode<T> child : current.getChildren()){
        // Longitud de la rama por este hijo = su altura + 1 arista que lo une con current
        int branch = 1 + diameterImpl(child);

        // Nos quedamos solo con las dos ramas más largas
        if(branch > first){
            second = first;
            first = branch;
        } else if(branch > second){
            second = branch;
        }
    }

    // El camino más largo que tiene a current como punto más alto une sus dos ramas más profundas
    // (por eso no tiene que pasar por la raíz: se evalúa en todos los nodos)
    if(first + second > this.diameter){
        this.diameter = first + second;
    }

    // Hacia el padre solo sube la rama más profunda
    return first;
}


/*Escribame por aca si necesita algo y hace push, esto me lo dio claude */



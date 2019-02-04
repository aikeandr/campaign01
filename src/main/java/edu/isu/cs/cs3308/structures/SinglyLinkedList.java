package edu.isu.cs.cs3308.structures;

/**
 * An implementation of a list, which uses a node to point to the next
 * node in a uni-directional path while keeping track of the first and last
 * element of the list.
 *
 * @author Andrew Aikens
 * @param <E> Element Type
 */
public class SinglyLinkedList<E> implements List<E> {

    /**
     * A node which only contains the element and the next node.
     *
     * @param <E>
     */
    private static class Node<E>{
        private E data;
        private Node<E> next;

        private Node(E data){
            this.data = data;
            this.next = null;
        }
        private Node(E data, Node<E> next){
            this.data = data;
            this.next = next;
        }
        private E getData(){
            return data;
        }
        public void setData(E data){
            this.data = data;
        }
        private Node<E> getNext(){
            return next;
        }
        private void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public SinglyLinkedList (){}
    /**
     * @return first element in the list or null if empty
     */
    @Override
    public E first() {
        if(isEmpty())
            return null;
        return head.getData();
    }

    /**
     *
     * @return last element in the list or null if empty
     */
    @Override
    public E last() {
        if(isEmpty())
            return null;
        return tail.getData();
    }

    /**
     * Adds the element to the tail of the list. If the list is empty,
     * the element becomes both the head and tail.
     *
     * @param element Element to be added to the end of the list.
     */
    @Override
    public void addLast(E element) {
        if(element == null)
            return;
        if(size == 0){
            head = new Node<>(element);
            tail = new Node<>(element);
        }
        else if(size == 1){
            Node<E> newNode = new Node<>(element);
            this.head.setNext(newNode);
            this.tail = newNode;
        }
        else {
            Node<E> newNode = new Node<>(element);
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the head of the list. If the list is empty,
     * the element becomes both the head and tail.
     *
     * @param element Element to be added to the front of the list.
     */
    @Override
    public void addFirst(E element) {
        if(element == null)
            return;
        if(size == 0){
            head = new Node<>(element);
            tail = new Node<>(element);
        }
        else
            head = new Node<>(element, head);
        size++;
    }

    /**
     * Removes the first element of the list
     *
     * @return the first element of the list or null if empty
     */
    @Override
    public E removeFirst() {
        if(this.isEmpty())
            return null;
        Node<E> tempNode = new Node<>(head.getData());
        head = head.getNext();
        tempNode.setNext(null);
        size--;
        return tempNode.getData();
    }

    /**
     * Removes the last element of the list
     *
     * @return the last element of the list or null if empty
     */
    @Override
    public E removeLast() {
        if(this.isEmpty())
            return null;

        Node<E> tempNode = head;
        for(int i = 1;i<size-1;i++)
            tempNode = tempNode.getNext();

        Node<E> toRemove = tempNode.getNext();
        tempNode.setNext(null);
        tail = tempNode;
        size--;
        return toRemove.getData();
    }

    /**
     * Inserts the element into a specified index or at the tail of the list
     * if the index is greater than the original size of the list.
     *
     * @param element Element to be added (as long as it is not null).
     * @param index Index in the list where the element is to be inserted.
     */
    @Override
    public void insert(E element, int index) {
        if(index < 0 || element == null)
            return;
        Node<E> toInsert = new Node<>(element);
        if(index > size) {
            tail.setNext(toInsert);
            tail = toInsert;
        }
        else {
            Node<E> tempNode = head;
            for (int i = 0; i < index - 1; i++) {
                tempNode = tempNode.getNext();
            }
            toInsert.setNext(tempNode.getNext());
            tempNode.setNext(toInsert);
        }
        size++;
    }

    /**
     * Removes an element at a given valid index.
     *
     * @param index Index of the element to remove
     * @return the element at the given index
     */
    @Override
    public E remove(int index) {
        if(index < 0 || index >= size)
            return null;

        Node<E> tempNode = head;
        for(int i = 0;i<index-1;i++)
            tempNode = tempNode.getNext();

        Node<E> toRemove = tempNode.getNext();
        tempNode.setNext(toRemove.getNext());
        size--;
        return toRemove.getData();
    }

    /**
     * Returns the element of a given index without affecting the list.
     *
     * @param index Index of the value to be retrieved.
     * @return the element at a given index
     */
    @Override
    public E get(int index) {
        if(index < 0 || index >= size)
            return null;

        Node<E> tempNode = head;
        for(int i = 0;i<index;i++)
            tempNode = tempNode.getNext();
        return tempNode.getData();
    }

    /**
     *
     * @return size of the list. 0 is considered empty
     */
    @Override
    public int size() {
        return size;
    }

    /**
     *
     * @return true if any elements are in the list. false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints the list.
     */
    @Override
    public void printList() {
        if(isEmpty())
            return;
        Node<E> tempNode = head;

        for(int i=0;i<size;i++){

            System.out.println(tempNode.getData());
            tempNode = tempNode.getNext();
        }
    }

    @Override
    public int indexOf(E item) {
        if (item == null || isEmpty())
            return -1;
        Node<E> tempNode = head;
        for(int i = 0; i < size(); i++){
            if(tempNode.getData().equals(item))
                return i;
            tempNode = tempNode.getNext();
        }
        return -1;
    }
}
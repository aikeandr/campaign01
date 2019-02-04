package edu.isu.cs.cs3308.structures;

import sun.rmi.runtime.NewThreadAction;

public class CircularlyLinkedList<E> implements List<E> {

    public static class Node<E>{
        private Node<E> next;
        private E data;

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
    private Node<E> tail;
    int size = 0;

    /**
     * Constructor for an empty CircularlyLinkedList.
     *
     */
    public CircularlyLinkedList(){}

    /**
     * @return first element in the list or null if the list is empty.
     */
    @Override
    public E first() {
        return tail.getNext().getData();
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
     * Adds the element to the tail of the list.
     *
     * @param element Element to be added to the end of the list.
     */
    @Override
    public void addLast(E element) {
        addFirst(element);
        tail = tail.getNext();
    }

    /**
     * Adds element to the front of the list.
     *
     * @param element Element to be added to the front of the list.
     */
    @Override
    public void addFirst(E element) {
        if(size == 0){
            tail = new Node<>(element);
            tail.setNext(tail);
        }
        else{
            Node<E> head = new Node<>(element, tail.getNext());
            tail.setNext(head);
        }
        size++;
    }

    /**
     * Removes the first element of the list
     *
     * @return first element in the list.
     */
    @Override
    public E removeFirst() {
        if(isEmpty())
            return null;
        Node<E> head = tail.getNext();
        if(head == tail)
            tail = null;
        else
            tail.setNext(head.getNext());
        size--;
        return head.getData();
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

        Node<E> tempNode = tail.getNext();
        for(int i = 1;i<size-1;i++)
            tempNode = tempNode.getNext();

        Node<E> toRemove = tempNode.getNext();
        tempNode.setNext(tail.getNext());
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
            Node<E> tempNode = tail.getNext();
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

        Node<E> tempNode = tail.getNext();
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

        Node<E> tempNode = tail.getNext();
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
        Node<E> tempNode = tail.getNext();

        for(int i=0;i<size;i++){

            System.out.print(tempNode.getData() + " ");
            tempNode = tempNode.getNext();
        }
    }

    @Override
    public int indexOf(E item) {
        if (item == null || isEmpty())
            return -1;
        Node<E> tempNode = tail.getNext();
        for(int i = 0; i < size(); i++){
            if(tempNode.getData().equals(item))
                return i;
            tempNode = tempNode.getNext();
        }
        return -1;
    }
    public void swapNext(int index){
        Node<E> temp = tail;
        for(int i = 0; i < index;i++)
            temp = temp.getNext();
        Node<E> swap = temp.getNext();
        temp.setNext(swap.getNext());
        swap.setNext(swap.getNext().getNext());
        temp.getNext().setNext(swap);
        if(index == 26)
            tail = swap;
        if(index == 27)
            tail = temp.getNext();
    }
    public void tripleCut(int above, int below){
        if(above > below){
            int swap = below;
            below = above;
            above = swap;
        }
        Node<E> secondJoker = tail.getNext();
        if(above == 0)
        {
            for (int i = 0; i < below; i++)
                secondJoker = secondJoker.getNext();
            tail = secondJoker;
        }
        else if(below == 27){
            Node<E> newTail = tail;
            for (int i = 0; i < above; i++)
                newTail = newTail.getNext();
            tail = newTail;
        }
        else {
            Node<E> newTail = tail;
            for (int i = 0; i < above; i++)
                newTail = newTail.getNext();
            Node<E> firstJoker = newTail.getNext();
            for (int i = 0; i < below; i++)
                secondJoker = secondJoker.getNext();
            newTail.setNext(secondJoker.getNext());
            secondJoker.setNext(tail.getNext());
            tail.setNext(firstJoker);
            tail = newTail;
        }
    }
    public void cutFront(int above){
        if(above == 28 || above == 27)
            return;
        Node<E> prev = tail;
        Node<E> newPrev = tail.getNext();
        for(int i = 0; i < size()-1;i++){
            if(i < above-1)
                newPrev = newPrev.getNext();
            prev = prev.getNext();
        }
        prev.setNext(tail.getNext());
        tail.setNext(newPrev.getNext());
        newPrev.setNext(tail);
    }
}

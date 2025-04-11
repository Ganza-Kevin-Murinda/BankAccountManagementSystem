package com.bankacc.bankaccessmanagementsystem;

/*
* In this class I will:
* create a LinkedList that will store transactions data
* methods to retrieve last N transactions
*/

public class TransactionLinkedList {

    private Node head; // points to the first node in the linked list
    private int size; // keeps track of how many transactions are in the list

    // Node inner class
    private class Node {
        Transaction transaction; // a transaction object
        Node next; // reference to the next node

        // constructor
        Node(Transaction transaction){
            this.transaction = transaction;
            this.next = null;
        }
    }

    // constructor
    public TransactionLinkedList() { // creates an initial empty list
        head = null;
        size = 0;
    }

    // Creates a method that add a transaction to the beginning of the list
    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        newNode.next = head; // at first, it will be null then after it will point to the current first node
        head = newNode; // new node becomes the head
        size++; // the size of the list grows as we add new nodes
    }

    // Creates a method that gets the last N transactions, the ones that are recent
    public Transaction[] getLastNTransactions(int n) {

        // returns an empty array if the user asks for 0 or negative number
        if( n <= 0)
            return new Transaction[0];

        int count = Math.min(n, size); // makes sure we don't get more transactions than actually exist

        // creates an array of size count(number of transaction user wants)
        Transaction[] transactions = new Transaction[count];

        // creates a temporary reference to go through the list, we can't move the head
        Node current = head;

        // traverse the list and store the transactions in the array
        for(int i = 0; i < count; i++) {
            transactions[i] = current.transaction;
            current = current.next;
        }
        return transactions;
    }

    // Creates a method that gets all the transactions in the list
    public Transaction[] getAllTransactions() {
        return getLastNTransactions(size);
    }

    // Creates a method that gets the number of transactions in the list
    public int getSize() {
        return size;
    }
}

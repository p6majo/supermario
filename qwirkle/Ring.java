package qwirkle;

/**
  *
  * Die Klasse repr�sentiert einen Ring, in dem beliebige Objekte gespeichert werden k�nnen.
  *
  * @version 2017-11-26
  * @author Daniel Garmann
  */

public class Ring<ContentType> {

  // Anfang Attribute
  private Node current;
  // Ende Attribute

  // Anfang Methoden
  
  private class Node {
    private ContentType content;
    private Node next;
    public Node() { next = null; content = null; }
    public Node(ContentType content) { next= null; this.content = content; }
    public Node(ContentType content, Node next) { this.next = next; this.content= content; }
    public void setNext(Node next) { this.next = next; }
    public void setContent(ContentType content) { this.content = content; }
    public Node getNext() { return next; }
    public ContentType getContent() { return content; }
  }
  
  /**
   * Ein leerer Ring wird erzeugt. Objekte, die in diesem Ring verwaltet werden,
   * m�ssen vom Typ ContentType sein.
   */
  public Ring() { current = null; }

  /**
   * Die Anfrage liefert den Wert true, wenn der Ring keine Objekte enth�lt, 
   * sonst liefert sie den Wert false.
   * 
   * @return true, wenn der Ring leer ist, sonst false
   */
  public boolean isEmpty() { return current == null; }

  /**
   * Falls der Ring nicht leer ist, wird das dem aktuellen Objekt in dem Ring 
   * folgende Objekt zum aktuellen Objekt, andernfalls geschieht nichts.
   */
  public void next() { if (!isEmpty()) current = current.getNext(); }

  /**
   * Die Anfrage liefert das aktuelle Objekt des Rings. Der Ring bleibt 
   * unver�ndert. Falls der Ring leer ist, wird null zur�ckgegeben.
   * 
   * @return das aktuelle Objekt (vom Typ ContentType) oder null, wenn der Ring leer ist.
   */
  public ContentType getContent() { if (!isEmpty()) return current.getContent(); else return null; }

  /**
   * Das Objekt pContent wird in den Ring hinter das das aktuelle Objekt 
   * eingef�gt und wird zum neuen aktuellen Objekt. Falls der Ring zuvor leer 
   * war, so besteht der Ring anschlie�end nur aus dem einen Objekt. 
   * Falls pContent gleich null ist, bleibt der Ring unver�ndert.
   * 
   * @param pContent das einzufuegende Objekt vom Typ ContentType
   */
  public void insert(ContentType pContent) {
    if (isEmpty()) {
      current = new Node(pContent);
      current.setNext(current);
    } else {
      Node tmp = new Node(pContent, current.getNext());
      current.setNext(tmp);
      current = tmp;
    }
  }
  
  /**
   * Die Anfrage liefert die Anzahl der im Ring gespeicherten Objekte. 
   * Falls der Ring leer ist, wird 0 zur�ckgegeben, andernfalls die Anzahl 
   * der im Ring gespeicherten Objekte. Der Ring wird nicht ver�ndert.
   * 
   * @return Anzahl der gespeicherten Objekte oder 0, falls der Ring leer ist.
   */
  public int count() {
    if (isEmpty()) return 0;
    int result = 1;
    Node tmp = current.getNext();
    while (tmp != current) {
      result++;
      tmp = tmp.getNext();
    }
    return result;
  }
  
  /**
   * Das aktuelle Objekt wird gel�scht und das Objekt hinter dem gel�schten 
   * Objekt wird zum aktuellen Objekt. Wird das letzte Objekt des Rings 
   * gel�scht, so gibt es kein aktuelles Objekt mehr. 
   * Wenn der Ring leer ist, bleibt der Ring unver�ndert.
   */
  public void remove() {
    if (!isEmpty()) {
      if (current.getNext() == current) {
        current = null;
      } else {
        Node tmp = current;
        while(tmp.getNext() != current) tmp = tmp.getNext();
        tmp.setNext(current.getNext());
        current = tmp.getNext();
      }
    }
  }
  

  // Ende Methoden
}

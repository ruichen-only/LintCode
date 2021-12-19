package com.restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class NoTableException extends Exception {

    public NoTableException(Party p) {
        super("No table available for party size: " + p.getSize());
    }
}

class Meal {
    private float price;

    public Meal(float price) {
        this.price = price;
    }

    public float getPrice() {
        return this.price;
    }
}

class Order {
    private List<Meal> meals;

    public Order() {
        meals = new ArrayList<>();
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void mergeOrder(Order order) {
        if(order != null) {
            for(Meal meal : order.getMeals()) {
                meals.add(meal);
            }
        }
    }

    public float getBill() {
        int bill = 0;
        for(Meal meal : meals) {
            bill += meal.getPrice();
        }
        return bill;
    }
}

class Party {
    private int size;

    public Party(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}

class Table implements Comparable<Table>{
    private int id;
    private int capacity;
    private boolean available;
    private Order order;
    List<Date> reservations;

    public Table(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        available = true;
        order = null;
        reservations = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Date> getReservation() {
        return reservations;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void markAvailable() {
        this.available = true;
    }

    public void markUnavailable() {
        this.available = false;
    }

    public Order getCurrentOrder() {
        return this.order;
    }

    public void setOrder(Order o) {
        if(order == null) {
            this.order = o;
        }
        else {
            if(o != null) {
                this.order.mergeOrder(o);
            }
        }
    }

    @Override
    public int compareTo(Table compareTable) {
        return this.capacity - compareTable.getCapacity();
    }

    private int findDatePosition(Date d) {
        int size = reservations.size();
        if (size == 0) return 0;

        if (d.getTime() > reservations.get(size - 1).getTime()) {
            return size;
        }
        int low = 0, high = size;
        while (low < high) {
            int mid = (low + high) / 2;
            if (d.getTime() > reservations.get(mid).getTime()) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high;
    }

    public boolean noFollowReservation(Date d) {
        int position = findDatePosition(d);
        if (position < reservations.size()) {
            Date next = reservations.get(position);
            int diff = (int)((d.getTime() - next.getTime()) / Restaurant.HOUR);
            if (diff < Restaurant.MAX_DINEHOUR) return false;
        }
        return true;
    }

    public boolean reserveForDate(Date d) {
        int pos = findDatePosition(d);
        int bef = pos - 1, aft = pos;

        if (bef >= 0) {
            Date preReserve = reservations.get(bef);
            int diff = (int)((d.getTime() - preReserve.getTime()) / Restaurant.HOUR);
            if (diff < Restaurant.MAX_DINEHOUR) return false;
        }
        if (aft < reservations.size()) {
            Date nextReserve = reservations.get(aft);
            int diff = (int)((d.getTime() - nextReserve.getTime()) / Restaurant.HOUR);
            if (diff < Restaurant.MAX_DINEHOUR) return false;
        }
        reservations.add(pos, d);
        return true;
    }

    public void removeReservation(Date d) {
        reservations.remove(d);
    }
}

class Reservation {
    private Table table;
    private Date date;

    public Reservation(Table table, Date date) {
        this.table = table;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Table getTable() {
        return table;
    }
}

public class Restaurant {
    private List<Table> tables;
    private List<Meal> menu;
    public static final int MAX_DINEHOUR = 2;
    public static final long HOUR = 3600*1000;

    public Restaurant() {
        tables = new ArrayList<Table>();
        menu = new ArrayList<Meal>();
    }

    public void findTable(Party p) throws NoTableException {
        Date curDate = new Date();
        for (Table table : tables) {
            if (table.isAvailable() &&
                table.getCapacity() >= p.getSize() &&
                table.noFollowReservation(curDate)) {
                table.markUnavailable();
                return;
            }
        }
        throw new NoTableException(p);
    }

    public void takeOrder(Table t, Order o) {
        t.setOrder(o);
    }

    public float checkOut(Table t) {
        float bill = 0;
        if (t.getCurrentOrder() != null) {
            bill = t.getCurrentOrder().getBill();
        }
        t.markAvailable();
        t.setOrder(null);
        return bill;
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public void addTable(Table t) {
        tables.add(t);
        Collections.sort(tables);
    }

    public Reservation findTableForReservation(Party p, Date date) {
        for (Table table : tables) {
            if (table.getCapacity() >= p.getSize()) {
                if (table.reserveForDate(date)) {
                    return new Reservation(table, date);
                }
            }
        }
        return null;
    }

    public void cancelReservation(Reservation r) {
        Date date = r.getDate();
        r.getTable().removeReservation(date);
    }

    public void redeemReservation(Reservation r) {
        Date date = r.getDate();
        Table table = r.getTable();

        table.markUnavailable();
        table.removeReservation(date);
    }

    public String restaurantDescription() {
        String description = "";
        for(int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            description += ("Table: " + table.getId() + ", table size: " + table.getCapacity() + ", isAvailable: " + table.isAvailable() + ".");
            if(table.getCurrentOrder() == null)
                description += " No current order for this table";
            else
                description +=  " Order price: " + table.getCurrentOrder().getBill();

            description += ". Current reservation dates for this table are: ";

            for(Date date : table.getReservation()) {
                description += date.toGMTString() + " ; ";
            }

            description += ".\n";
        }
        description += "*****************************************\n";
        return description;
    }
}

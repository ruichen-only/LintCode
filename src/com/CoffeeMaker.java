package com;

public class CoffeeMaker {

    public Coffee makeCoffee(CoffeePack pack) {
        Coffee coffee = new SimpleCoffee();
        int neededMilk  = pack.getNeededMilk();
        int neededSugar = pack.getNeededSugar();
        while (neededMilk-- > 0) {
            coffee = new WithMilk(coffee);
        }
        while (neededSugar-- > 0) {
            coffee = new WithSugar(coffee);
        }
        return coffee;
    }
}

class CoffeePack {
    private int neededMilk;
    private int neededSugar;

    public CoffeePack(int neededMilk, int neededSugar) {
        this.neededMilk = neededMilk;
        this.neededSugar = neededSugar;
    }

    public int getNeededMilk() {
        return neededMilk;
    }

    public int getNeededSugar() {
        return neededSugar;
    }
}

interface Coffee {
    public double getCost();
    public String getIngredients();
}

class SimpleCoffee implements Coffee {

    @Override
    public double getCost() {
        return 2;
    }

    @Override
    public String getIngredients() {
        return "Plain Coffee";
    }

}

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }

    public String getIngredients() {
        return decoratedCoffee.getIngredients();
    }
}

class WithMilk extends CoffeeDecorator {

    public WithMilk(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getIngredients() {
        return super.getIngredients() + ", Milk";
    }
}

class WithSugar extends CoffeeDecorator {

    public WithSugar(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getIngredients() {
        return super.getIngredients() + ", Sugar";
    }
}
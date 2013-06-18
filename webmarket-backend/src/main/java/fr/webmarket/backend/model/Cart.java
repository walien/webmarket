package fr.webmarket.backend.model;

import fr.webmarket.backend.features.commercial.Coupon;

import java.util.List;
import java.util.Set;

public class Cart {

    private List<OrderLine> lines;

    private Set<String> couponsKeys;

    private Set<Coupon> coupons;

    private double amount;

    public Cart() {

    }

    public Cart(List<OrderLine> lines, Set<String> couponsKeys, Set<Coupon> coupons) {
        this.lines = lines;
        this.couponsKeys = couponsKeys;
        this.coupons = coupons;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    public Set<String> getCouponsKeys() {
        return couponsKeys;
    }

    public void setCouponsKeys(Set<String> couponsKeys) {
        this.couponsKeys = couponsKeys;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "lines=" + lines +
                ", couponsKeys=" + couponsKeys +
                ", coupons=" + coupons +
                ", amount=" + amount +
                '}';
    }
}

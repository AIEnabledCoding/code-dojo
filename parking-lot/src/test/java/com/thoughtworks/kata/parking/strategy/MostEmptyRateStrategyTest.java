package com.thoughtworks.kata.parking.strategy;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.kata.parking.Car;
import com.thoughtworks.kata.parking.Parkable;
import com.thoughtworks.kata.parking.ParkingLot;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class MostEmptyRateStrategyTest {

    private ParkableChoosingStrategy parkableChoosingStrategy;
    private ParkingLot moreEmptyParkingLot;
    private ParkingLot lessEmptyParkingLot;

    @Before
    public void setUp() {
        parkableChoosingStrategy = new MostEmptyRateStrategy();
        moreEmptyParkingLot = new ParkingLot(15);
        moreEmptyParkingLot.park(new Car("BMW: Z-4"));
        moreEmptyParkingLot.park(new Car("BMW: Z-4"));
        lessEmptyParkingLot = new ParkingLot(5);
        lessEmptyParkingLot.park(new Car("BMW: Z-4"));
    }

    @Test
    public void should_return_first_parking_lot_if_first_has_more() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(moreEmptyParkingLot, lessEmptyParkingLot));
        assertThat(parkingLot.get(), sameInstance((Parkable) moreEmptyParkingLot));
    }

    @Test
    public void should_return_first_parking_lot_if_both_are_the_same() throws Exception {
        moreEmptyParkingLot.park(new Car("BMW: Z-4"));
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(moreEmptyParkingLot, lessEmptyParkingLot));
        assertThat(parkingLot.get(), sameInstance((Parkable) moreEmptyParkingLot));
    }

    @Test
    public void should_return_second_parking_lot_if_second_has_more() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(lessEmptyParkingLot, moreEmptyParkingLot));
        assertThat(parkingLot.get(), sameInstance((Parkable) moreEmptyParkingLot));
    }

    @Test
    public void should_return_absent_if_all_parking_lots_are_full() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(new ParkingLot(0)));
        assertThat(parkingLot.isPresent(), is(false));
    }

    @Test
    public void should_return_absent_if_there_is_not_a_parking_lot() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of());
        assertThat(parkingLot.isPresent(), is(false));
    }
}

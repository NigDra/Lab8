package edu.eci.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import edu.eci.models.Car;
import edu.eci.models.User;
import edu.eci.persistences.CarMemoryRepository;
import edu.eci.persistences.repositories.ICarRepository;
import edu.eci.services.contracts.ICarServices;

public class CarServices implements ICarServices{
	@Autowired
	@Qualifier("CarMemoryRepository")
	private CarMemoryRepository carRepository;
	
	public List<Car> list() {
		return carRepository.findAll();
	}
	
	
	public Car create(Car car) {
		if (null == car.getLicencePlate())
			throw new RuntimeException("Plate invalid");
		else if (carRepository.find(car.getLicencePlate()) != null)
			throw new RuntimeException("The car exists");
		else
			carRepository.save(car);
		return car;
	}


	@Override
	public Car get(String plate) {
		List<Car> cars = list();
		Car r =null;
		for (Car c : cars) {
			if (c.getLicencePlate().equals(plate)) {
				r=c;
			}
		}
		if(r.equals(null)) {
			throw new RuntimeException("The car doesn't exists");
		}
		return r;
	}


	@Override
	public void updateCar(Car ce) {
		List<Car> cars = list();
		boolean h =false;
		for (Car c : cars) {
			if (c.getBrand().equals(ce.getBrand()) && c.getLicencePlate().equals(ce.getLicencePlate())) {
				c.setBrand(ce.getBrand());
				c.setLicencePlate(ce.getLicencePlate());
				h=true;
			}
		}
		if(!h) {
			throw new RuntimeException("The car doesn't exists");
		}
		
	}


	@Override
	public void delete(String plate) {
		carRepository.remove(plate);
		
	}
	
	
	
	

}
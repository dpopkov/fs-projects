import DialogContent from '@mui/material/DialogContent';
import { Car } from '../types';

type DialogFormProps = {
  car: Car;
  handleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
};

function CarDialogContent({ car, handleChange }: DialogFormProps) {
  return (
    <DialogContent>
      <input
        placeholder="Brand"
        name="brand"
        value={car.brand}
        onChange={handleChange}
      />
      <input
        placeholder="Model"
        name="model"
        value={car.model}
        onChange={handleChange}
      />
      <input
        placeholder="Color"
        name="color"
        value={car.color}
        onChange={handleChange}
      />
      <input
        placeholder="Year"
        name="modelYear"
        value={car.modelYear}
        onChange={handleChange}
      />
      <input
        placeholder="Reg.nr"
        name="registrationNumber"
        value={car.registrationNumber}
        onChange={handleChange}
      />
      <input
        placeholder="Price"
        name="price"
        value={car.price}
        onChange={handleChange}
      />
    </DialogContent>
  );
}

export default CarDialogContent;

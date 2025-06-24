package sage.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AmbienteSimulation {

    private List<SensorSimulation> sensores;
    private Consumer<String> output = System.out::println;

    public AmbienteSimulation() {
        sensores = new ArrayList<>();
    }

    public void setOutput(Consumer<String> output) {
        this.output = output;
    }

    public void addSensor(SensorSimulation sensor) {
        sensor.setOutput(this.output);
        sensores.add(sensor);
    }

    public void runCycles(int qtd) throws InterruptedException {
        for (int i = 0; i < qtd; i++) {
            output.accept("\n\t--- Ciclo " + (i + 1) + " ---");
            for (SensorSimulation s : sensores) {
                s.moviments();
            }
            Thread.sleep(3000);
            for (SensorSimulation s : sensores) {
                s.idleCheck();
            }
            Thread.sleep(3000);
        }
    }
}

package com.verix.apm.domain.model;

// sirve como un stub o un objeto de prueba para crear instancias predefinidas de la clase Apm.
// En lugar de repetir la inicialización de Apm en cada test, simplemente llamas a ApmStub.create()

public final class ApmStub {
    public static Apm create() {
        return new Apm(
                "B3TT",
                "Ahorro Leasing",
                "Yes",
                "No",
                "Production",
                LifeDate.create("01-Oct-2009"),
                LifeDate.create("01-Oct-2009"),
                "Medium: Operational within 10 days",
                "Yes",
                "Juan Oyaneder Bello",
                "Gerardo Mc Cabe Rodriguez",
                "Jorge Emilio Onate Bernal",
                "Francisco Palma Maturana",
                "Francisco Palma Maturana",
                "Alvaro Mauricio Taricco Lopez",
                "CL");
    }
}

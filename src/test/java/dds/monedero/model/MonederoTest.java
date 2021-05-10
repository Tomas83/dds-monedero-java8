package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;
  private Cuenta cuentaCara;

  @BeforeEach
  void init() {
	    cuenta = new Cuenta(5000);
  }

  @Test
  void Poner() {
    cuenta.poner(1500);
    assertEquals(6500,cuenta.getSaldo(),0.1);
  }

  @Test
  void Sacar() {
    cuenta.sacar(500);
    assertEquals(4500,cuenta.getSaldo(),0.1);
  }

  @Test
  void RegistrarMovimientos() {
	    cuenta.sacar(500);
	    cuenta.poner(500);
	    cuenta.sacar(200);
	    cuenta.poner(1500);
    assertEquals(4,cuenta.getMovimientos().size(),0.1);
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }
  
  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.sacar(5001);
    });
  }
  @Test
  public void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.sacar(1001);
    });
  }
  @Test
  public void ExtraerMasDe1000DeABaches() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
    	cuenta.sacar(999);
    	cuenta.sacar(2);
    });
  }
  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }
/*
*/
}
package com.example.z_iti_271304_u2_e01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button btnMoveForward, btnTurnLeft, btnTurnRight, btnDelete, btnRefresh;
    private ImageView robot;

    private List<String> instructions = new ArrayList<>();
    private int currentPositionX = 0;
    private int currentPositionY = 0;
    private String direction = "UP";  // Direcciones posibles: "UP", "DOWN", "LEFT", "RIGHT"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        gridLayout = findViewById(R.id.gridLayout);
        btnMoveForward = findViewById(R.id.btn_move_forward);
        btnTurnLeft = findViewById(R.id.btn_turn_left);
        btnTurnRight = findViewById(R.id.btn_turn_right);
        btnDelete = findViewById(R.id.btn_delete);
        btnRefresh = findViewById(R.id.btn_refresh);

        // Inicializa la cuadrícula de 10x10 y el robot en la posición inicial
        initializeGrid();

        // Botón para mover hacia adelante
        btnMoveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveForward();
                Toast.makeText(MainActivity.this, "Mover Adelante", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para girar a la izquierda
        btnTurnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnLeft();
                Toast.makeText(MainActivity.this, "Girar Izquierda", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para girar a la derecha
        btnTurnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnRight();
                Toast.makeText(MainActivity.this, "Girar Derecha", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para refrescar (reiniciar) las instrucciones y la posición del robot
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPositionX = 0;
                currentPositionY = 0;
                direction = "UP";  // Reiniciar la dirección a "arriba"
                updateRobotPosition();
                Toast.makeText(MainActivity.this, "Posición y dirección reiniciadas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Inicializa la cuadrícula de 10x10
    private void initializeGrid() {
        gridLayout.setColumnCount(10);  // Asegúrate de que el GridLayout tenga 10 columnas

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ImageView cell = new ImageView(this);
                cell.setBackgroundResource(R.drawable.cell_border);  // Aplica el fondo con bordes

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 120;  // Tamaño de cada celda
                params.height = 120;
                cell.setLayoutParams(params);
                gridLayout.addView(cell);
            }
        }

        // Inicializa el robot en la posición inicial (esquina superior izquierda)
        robot = new ImageView(this);
        robot.setImageResource(R.drawable.robot_icon);  // Usa una imagen para el robot

        // Ajusta el tamaño del robot
        GridLayout.LayoutParams robotParams = new GridLayout.LayoutParams();
        robotParams.width = 50;  // Tamaño del robot
        robotParams.height = 50;
        robot.setLayoutParams(robotParams);

        updateRobotPosition();  // Coloca el robot en la posición inicial
    }


    // Mueve el robot a la posición correcta en la cuadrícula
    private void updateRobotPosition() {
        // Primero eliminamos el robot de su posición actual
        gridLayout.removeView(robot);

        // Calculamos el índice en la cuadrícula usando las coordenadas X e Y
        int index = currentPositionY * 10 + currentPositionX;

        // Asegúrate de que el índice esté dentro del rango correcto
        if (index >= 0 && index < gridLayout.getChildCount()) {
            // Ajusta el margen del robot para centrarlo en la celda
            GridLayout.LayoutParams robotParams = new GridLayout.LayoutParams();
            robotParams.width = 50;  // Tamaño del robot
            robotParams.height = 50;
            robotParams.setMargins(5, 5, 5, 5);  // Margen para centrar el robot
            robot.setLayoutParams(robotParams);

            // Añade el robot en la nueva posición
            gridLayout.addView(robot, index);
        } else {
            Toast.makeText(this, "Índice fuera de rango", Toast.LENGTH_SHORT).show();  // Mensaje de error si algo está mal
        }
    }

    // Función para mover el robot adelante en la dirección actual
    private void moveForward() {
        switch (direction) {
            case "UP":
                if (currentPositionY > 0) {
                    currentPositionY--;
                    updateRobotPosition();
                } else {
                    Toast.makeText(this, "No puedes salirte de la cuadrícula", Toast.LENGTH_SHORT).show();
                }
                break;
            case "DOWN":
                if (currentPositionY < 9) {  // Cambiar a 9 para 10x10
                    currentPositionY++;
                    updateRobotPosition();
                } else {
                    Toast.makeText(this, "No puedes salirte de la cuadrícula", Toast.LENGTH_SHORT).show();
                }
                break;
            case "LEFT":
                if (currentPositionX > 0) {
                    currentPositionX--;
                    updateRobotPosition();
                } else {
                    Toast.makeText(this, "No puedes salirte de la cuadrícula", Toast.LENGTH_SHORT).show();
                }
                break;
            case "RIGHT":
                if (currentPositionX < 9) {  // Cambiar a 9 para 10x10
                    currentPositionX++;
                    updateRobotPosition();
                } else {
                    Toast.makeText(this, "No puedes salirte de la cuadrícula", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // Función para girar a la izquierda
    private void turnLeft() {
        switch (direction) {
            case "UP":
                direction = "LEFT";
                break;
            case "LEFT":
                direction = "DOWN";
                break;
            case "DOWN":
                direction = "RIGHT";
                break;
            case "RIGHT":
                direction = "UP";
                break;
        }
    }

    // Función para girar a la derecha
    private void turnRight() {
        switch (direction) {
            case "UP":
                direction = "RIGHT";
                break;
            case "RIGHT":
                direction = "DOWN";
                break;
            case "DOWN":
                direction = "LEFT";
                break;
            case "LEFT":
                direction = "UP";
                break;
        }
    }
}

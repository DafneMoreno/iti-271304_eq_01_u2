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
    private Button btnMoveForward, btnTurnLeft, btnTurnRight, btnPlay, btnDelete, btnRefresh;
    private ImageView robot;

    private List<String> instructions = new ArrayList<>();
    private int currentPositionX = 0;
    private int currentPositionY = 0;
    private String direction = "UP";  // Puede ser "UP", "DOWN", "LEFT", "RIGHT"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        gridLayout = findViewById(R.id.gridLayout);
        btnMoveForward = findViewById(R.id.btn_move_forward);
        btnTurnLeft = findViewById(R.id.btn_turn_left);
        btnTurnRight = findViewById(R.id.btn_turn_right);
        btnPlay = findViewById(R.id.btn_play);
        btnDelete = findViewById(R.id.btn_delete);
        btnRefresh = findViewById(R.id.btn_refresh);

        // Inicializa la cuadrícula de 25x25 y el robot en la posición inicial
        initializeGrid();

        // Configura los botones para agregar instrucciones
        btnMoveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.add("MOVE_FORWARD");
                Toast.makeText(MainActivity.this, "Instrucción: Mover Adelante", Toast.LENGTH_SHORT).show();
            }
        });

        btnTurnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.add("TURN_LEFT");
                Toast.makeText(MainActivity.this, "Instrucción: Girar Izquierda", Toast.LENGTH_SHORT).show();
            }
        });

        btnTurnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.add("TURN_RIGHT");
                Toast.makeText(MainActivity.this, "Instrucción: Girar Derecha", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón de Play para ejecutar las instrucciones
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String instruction : instructions) {
                    switch (instruction) {
                        case "MOVE_FORWARD":
                            moveForward();
                            break;
                        case "TURN_LEFT":
                            turnLeft();
                            break;
                        case "TURN_RIGHT":
                            turnRight();
                            break;
                    }
                }
            }
        });

        // Botón para eliminar la última instrucción
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!instructions.isEmpty()) {
                    instructions.remove(instructions.size() - 1);
                    Toast.makeText(MainActivity.this, "Última instrucción eliminada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para refrescar (reiniciar) las instrucciones y la posición del robot
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.clear();
                currentPositionX = 0;
                currentPositionY = 0;
                direction = "UP";
                updateRobotPosition();
                Toast.makeText(MainActivity.this, "Instrucciones reiniciadas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Inicializa la cuadrícula de 25x25
    private void initializeGrid() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                ImageView cell = new ImageView(this);
                cell.setBackgroundResource(R.drawable.cell_background);  // Definir en drawable
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 40;
                params.height = 40;
                cell.setLayoutParams(params);
                gridLayout.addView(cell);
            }
        }

        // Añadir el robot en la posición inicial (esquina superior izquierda)
        robot = new ImageView(this);
        robot.setImageResource(R.drawable.robot_icon);  // Icono del robot
        updateRobotPosition();
    }

    // Mueve el robot a la posición correcta en la cuadrícula
    private void updateRobotPosition() {
        gridLayout.removeView(robot);  // Remueve el robot de su posición anterior
        int index = currentPositionY * 25 + currentPositionX;
        gridLayout.addView(robot, index);
    }

    // Función para mover el robot adelante dependiendo de la dirección
    private void moveForward() {
        switch (direction) {
            case "UP":
                if (currentPositionY > 0) currentPositionY--;
                break;
            case "DOWN":
                if (currentPositionY < 24) currentPositionY++;
                break;
            case "LEFT":
                if (currentPositionX > 0) currentPositionX--;
                break;
            case "RIGHT":
                if (currentPositionX < 24) currentPositionX++;
                break;
        }
        updateRobotPosition();
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

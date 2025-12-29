#!/bin/bash

# Pedestrian and Bicyclist Safety Tool Launcher
# Runs the application with NVIDIA GPU acceleration for optimal 3D rendering

__NV_PRIME_RENDER_OFFLOAD=1 __GLX_VENDOR_LIBRARY_NAME=nvidia java -cp "bin:lib/*" aProject.MIS_Project
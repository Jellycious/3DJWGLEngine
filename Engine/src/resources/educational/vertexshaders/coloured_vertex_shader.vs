#version 420

layout (location=0) in vec3 position;
layout (location=1) in vec3 inColor;

out vec3 exColor;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main()
{
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
	exColor = inColor;
}















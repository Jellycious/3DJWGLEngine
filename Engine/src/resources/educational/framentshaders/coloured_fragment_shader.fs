#version 420

out vec4 fragColor;
in vec3 exColor;

void main()
{
    fragColor = vec4(exColor, 1.0);
}
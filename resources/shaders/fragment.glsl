#version 420

in vec3 color;
in vec3 pos;
out vec4 frag_color;

void main() {
    float b = (int(pos.x) + int(pos.z)) % 2;
    if (b == -1) {
        b = 1;
    }

//    frag_color = vec4(pos.y - floor(pos.y), g, b, 1.0);
    frag_color = vec4(pos.y - floor(pos.y), b, b, 1.0);
}
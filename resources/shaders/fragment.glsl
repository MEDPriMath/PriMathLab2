#version 420

in vec3 color;
in vec3 pos;
//in float out_min;
//in float out_max;

out vec4 frag_color;

void main() {
    float b = (int(pos.x) + int(pos.z)) % 2;
    if (b == -1) {
        b = 1;
    }

    //    frag_color = vec4(pos.y - floor(pos.y), g, b, 1.0);
    frag_color = vec4(pos.y - floor(pos.y), b, b, 1.0);

//    float b = (int(pos.x) + int(pos.z)) % 2;
//    if (b == -1) {
//        b = 1;
//    }
//
//    //    frag_color = vec4(pos.y - floor(pos.y), g, b, 1.0);
//    frag_color = vec4(pos.y - floor(pos.y), b, b, 1.0);
//
////     float b = (int(pos.x) + int(pos.z)) % 2;
////     if (b == -1) {
////         b = 1;
////     }
//
////    float k = pos.y - floor(pos.y);
//
////    float red = 1 - ((pos.y - out_min) / (out_max - out_min));
////    float green = ((pos.y - out_min) / (out_max - out_min));
//
////    if (pos.y * 20 - floor(pos.y * 20) < 0.07) {
////        frag_color = vec4(0.5 * red, 0.5 * green, 0, 1);
////    } else {
////        frag_color = vec4(red, green, 0, 1);
////    }
}